package com.simple_shop.item_service.item_image.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item_image.model.ConfirmUploadResponse;
import com.simple_shop.item_service.item_image.model.ConfirmUploadResponse.ConfirmUploadResponseBuilder;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest;
import com.simple_shop.item_service.item_image.model.ImageState;
import com.simple_shop.item_service.item_image.model.ImageType;
import com.simple_shop.item_service.item_image.model.ItemImage;
import com.simple_shop.item_service.item_image.model.ItemImage.ItemImageBuilder;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse.RequestResourcesResponseBuilder;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlRequest;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse.RequestUploadUrlResponseBuilder;
import com.simple_shop.item_service.item_image.persistence.ItemImageTransactionHandler;
import com.simple_shop.item_service.item_image.storage.StorageClient;
import com.simple_shop.item_service.shared.exceptions.InvalidMessageException;
import com.simple_shop.item_service.shared.utils.ItemServiceUtils;

@Service
public class ItemImageService {
	private final ItemImageTransactionHandler dbTransactionHandler;

	private final StorageClient storageClient;

	public ItemImageService(//
			final ItemImageTransactionHandler itemImageTransactionHandler, //
			final StorageClient storageClient) {

		super();

		this.dbTransactionHandler = itemImageTransactionHandler;
		this.storageClient = storageClient;
	}

	public RequestUploadUrlResponse requestUploadUrl(final long itemId, final RequestUploadUrlRequest request) {
		this.ensureOnlyOneThumbnailExists(itemId, request);

		/*
		 * TODO Make sure there are no collisions. This request could result in multiple
		 * item services from picking the same ID for the same item, even though it
		 * might be unlikely.
		 */
		final String uniqueId = UUID.randomUUID().toString();

		final String storageKey = new StringBuilder()//
				.append("uploads/item-images/")//
				.append(itemId).append("/")//
				.append(uniqueId).append(".").append(request.getFileType().toLowerCase())//
				.toString();

		final ItemImage pendingImage = new ItemImageBuilder()//
				.setItemId(itemId)//
				.setStorageKey(storageKey)//
				.setImageType(request.getImageType())//
				.setFileType(request.getFileType())//
				.setImageState(ImageState.PENDING)//
				.setCreatedAt(ItemServiceUtils.createNow())//
				.build();

		final ItemImage savedImage = this.dbTransactionHandler.saveItemImage(pendingImage);

		final String uploadUrl = this.storageClient.generatePreSignedUploadUrl(//
				savedImage.getStorageKey(), //
				this.determineMimeType(request.getFileType()), //
				request.getFileSizeInBytes());

		return new RequestUploadUrlResponseBuilder()//
				.setUploadUrl(uploadUrl)//
				.setStorageKey(savedImage.getStorageKey())//
				.build();
	}

	private String determineMimeType(final String fileType) {
		final String mimeType;

		final String lowerCaseFileType = fileType.toLowerCase();
		if (lowerCaseFileType.equals("jpg") || lowerCaseFileType.equals("jpeg")) {
			mimeType = "image/jpeg";
		} else if (lowerCaseFileType.equals("webp")) {
			mimeType = "image/webp";
		} else {
			throw new IllegalArgumentException("Unsupported file type: " + fileType);
		}

		return mimeType;
	}

	private void ensureOnlyOneThumbnailExists(final long itemId, final RequestUploadUrlRequest request) {
		if (ImageType.THUMBNAIL.equals(request.getImageType())) {
			final List<ItemImage> thumbnails = this.dbTransactionHandler.findByItemIdAndImageType(itemId,
					ImageType.THUMBNAIL);
			if (thumbnails.size() > 1) {
				throw new InvalidMessageException(
						"There can only be one thumbnail! Remove the existing one to add a new one.");
			}
		}
	}

	public ConfirmUploadResponse confirmUpload(final long itemId, final ConfirmUploadRequest request) {
		final String requestStorageKey = request.getStorageKey();

		final boolean bucketObjectExists = this.storageClient.doesObjectExist(requestStorageKey);
		final ItemImage itemImage = this.dbTransactionHandler.findByStorageKey(requestStorageKey);
		final boolean requestIsValid = bucketObjectExists && itemImage != null
				&& ImageState.PENDING.equals(itemImage.getImageState()) && itemImage.getItemId() == itemId;

		if (!requestIsValid) {
			throw new InvalidMessageException("The confirmation request is invalid!");
		}

		final String newStorageKey = new StringBuilder()//
				.append("item-images/")//
				.append(itemId).append("/")//
				.append(itemImage.getId()).append(".")//
				.append(itemImage.getFileType().toLowerCase())//
				.toString();
		this.storageClient.copyObject(requestStorageKey, newStorageKey);
		this.storageClient.deleteObject(requestStorageKey);

		final ItemImage updatedItemImage = ItemImageBuilder.initFrom(itemImage)//
				.setImageState(ImageState.ACTIVE)//
				.setStorageKey(newStorageKey)//
				.build();
		this.dbTransactionHandler.saveItemImage(updatedItemImage);

		return new ConfirmUploadResponseBuilder()//
				.setStorageKey(newStorageKey)//
				.build();
	}

	public void deleteImage(final long itemId, final DeleteResourceRequest request) {
		final String requestStorageKey = request.getStorageKey();

		final boolean bucketObjectExists = this.storageClient.doesObjectExist(requestStorageKey);
		final ItemImage itemImage = this.dbTransactionHandler.findByStorageKey(requestStorageKey);
		final boolean requestIsValid = bucketObjectExists && itemImage != null
				&& ImageState.ACTIVE.equals(itemImage.getImageState()) && itemImage.getItemId() == itemId;

		if (!requestIsValid) {
			throw new InvalidMessageException("The deletion request is invalid!");
		}

		this.dbTransactionHandler.deleteByStorageKey(requestStorageKey);
		this.storageClient.deleteObject(requestStorageKey);
	}

	/**
	 * This is a method provided solely for inter-service activities. When e.g. the
	 * item service wants to delete an item, there's no need to remove all item
	 * image instances one by one from the database, as there's an on delete cascade
	 * approach, which is more efficient.
	 *
	 * @param storageKeys The storage keys
	 */
	public void deleteImagesOnly(final List<String> storageKeys) {
		for (final String storageKey : storageKeys) {
			this.storageClient.deleteObject(storageKey);
		}
	}

	public RequestResourcesResponse getImages(final long itemId) {
		final List<ItemImage> itemImages = this.dbTransactionHandler.findByItemIdAndImageState(itemId,
				ImageState.ACTIVE);

		final RequestResourcesResponseBuilder builder = new RequestResourcesResponseBuilder();

		final List<String> galleryImageStorageKeys = itemImages.stream()//
				.filter(i -> ImageType.GALLERY.equals(i.getImageType()))//
				.map(ItemImage::getStorageKey)//
				.toList();
		builder.setGalleryImageStorageKeys(galleryImageStorageKeys);

		final List<String> thumbnailImageStorageKeys = itemImages.stream()//
				.filter(i -> ImageType.THUMBNAIL.equals(i.getImageType()))//
				.map(ItemImage::getStorageKey)//
				.toList();
		if (!thumbnailImageStorageKeys.isEmpty()) {
			// Simply pick the first one. So far there should only be one at max.
			builder.setThumbnailImageStorageKey(thumbnailImageStorageKeys.get(0));
		}

		return builder.build();
	}

	public void cleanExpiredPendingImages(final LocalDateTime threshold) {
		Objects.requireNonNull(threshold);

		final List<String> itemImageStorageKeys = this.dbTransactionHandler
				.findByImageStateAndCreatedAtBefore(ImageState.PENDING, threshold).stream()//
				.map(ItemImage::getStorageKey)//
				.toList();

		this.dbTransactionHandler.deleteByStorageKeyIn(itemImageStorageKeys);
		this.storageClient.deleteObjects(itemImageStorageKeys);
	}
}
