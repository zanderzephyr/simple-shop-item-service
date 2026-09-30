package com.simple_shop.item_service.item_image.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest;
import com.simple_shop.item_service.item_image.model.ImageState;
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
	private final ItemImageTransactionHandler itemImageTransactionHandler;

	private final StorageClient storageClient;

	public ItemImageService(//
			final ItemImageTransactionHandler itemImageTransactionHandler, //
			final StorageClient storageClient) {

		super();

		this.itemImageTransactionHandler = itemImageTransactionHandler;
		this.storageClient = storageClient;
	}

	public RequestUploadUrlResponse requestUploadUrl(final long itemId, final RequestUploadUrlRequest request) {
		/*
		 * TODO Make sure there are no collisions. This request could result in multiple
		 * item services from picking the same ID for the same item, even though it
		 * might be unlikely.
		 */
		final String uniqueId = UUID.randomUUID().toString();

		final String storageKey = new StringBuilder()//
				.append("uploads/item-images/")//
				.append(itemId).append("/")//
				.append(uniqueId).append(".").append(request.getImageFileType().name().toLowerCase())//
				.toString();

		final ItemImage pendingImage = new ItemImageBuilder()//
				.setItemId(itemId)//
				.setStorageKey(storageKey)//
				.setImageType(request.getImageType())//
				.setImageFileType(request.getImageFileType())//
				.setImageState(ImageState.PENDING)//
				.setCreatedAt(ItemServiceUtils.createNow())//
				.build();

		final ItemImage savedImage = this.itemImageTransactionHandler.saveItemImage(pendingImage);

		final String uploadUrl = this.storageClient.generatePreSignedUploadUrl(savedImage.getStorageKey());
		return new RequestUploadUrlResponseBuilder()//
				.setUploadUrl(uploadUrl)//
				.setStorageKey(savedImage.getStorageKey())//
				.build();
	}

	public void confirmUpload(final long itemId, final ConfirmUploadRequest request) {
		final String requestStorageKey = request.getStorageKey();

		final boolean bucketObjectExists = this.storageClient.doesObjectExist(requestStorageKey);
		final ItemImage itemImage = this.itemImageTransactionHandler.findByStorageKey(requestStorageKey);
		final boolean requestIsValid = bucketObjectExists && itemImage != null
				&& ImageState.PENDING.equals(itemImage.getImageState()) && itemImage.getItemId() == itemId;

		if (!requestIsValid) {
			throw new InvalidMessageException("The confirmation request is invalid!");
		}

		final String newStorageKey = new StringBuilder()//
				.append("item-images/")//
				.append(itemId).append("/")//
				.append(itemImage.getId()).append(".")//
				.append(itemImage.getImageFileType().name().toLowerCase())//
				.toString();
		this.storageClient.copyObject(requestStorageKey, newStorageKey);
		this.storageClient.deleteObject(requestStorageKey);

		final ItemImage updatedItemImage = ItemImageBuilder.initFrom(itemImage)//
				.setImageState(ImageState.ACTIVE)//
				.setStorageKey(newStorageKey)//
				.build();
		this.itemImageTransactionHandler.saveItemImage(updatedItemImage);
	}

	public void deleteImage(final long itemId, final DeleteResourceRequest request) {
		final String requestStorageKey = request.getStorageKey();

		final boolean bucketObjectExists = this.storageClient.doesObjectExist(requestStorageKey);
		final ItemImage itemImage = this.itemImageTransactionHandler.findByStorageKey(requestStorageKey);
		final boolean requestIsValid = bucketObjectExists && itemImage != null
				&& ImageState.ACTIVE.equals(itemImage.getImageState()) && itemImage.getItemId() == itemId;

		if (!requestIsValid) {
			throw new InvalidMessageException("The deletion request is invalid!");
		}

		this.itemImageTransactionHandler.deleteByStorageKey(requestStorageKey);
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
		final List<ItemImage> itemImages = this.itemImageTransactionHandler.findByItemIdAndImageState(itemId,
				ImageState.ACTIVE);

		return new RequestResourcesResponseBuilder()//
				.setStorageKeys(itemImages.stream()//
						.map(ItemImage::getStorageKey)//
						.toList())//
				.build();
	}
}
