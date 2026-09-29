package com.simple_shop.item_service.item_image.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item.exceptions.InvalidMessageException;
import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest;
import com.simple_shop.item_service.item_image.model.ImageState;
import com.simple_shop.item_service.item_image.model.ItemImage;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlRequest;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.item_image.model.ItemImage.ItemImageBuilder;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse.RequestUploadUrlResponseBuilder;
import com.simple_shop.item_service.item_image.persistence.ItemImageTransactionHandler;
import com.simple_shop.item_service.item_image.storage.StorageClient;

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
				.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC))//
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
			throw new InvalidMessageException("The confirmation message is invalid!");
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

	}
}
