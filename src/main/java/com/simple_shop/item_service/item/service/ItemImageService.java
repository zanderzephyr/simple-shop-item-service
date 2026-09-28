package com.simple_shop.item_service.item.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item.model.ImageState;
import com.simple_shop.item_service.item.model.ImageType;
import com.simple_shop.item_service.item.model.ItemImage;
import com.simple_shop.item_service.item.model.ItemImage.ItemImageBuilder;
import com.simple_shop.item_service.item.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.item.model.RequestUploadUrlResponse.RequestUploadUrlResponseBuilder;
import com.simple_shop.item_service.item.persistence.ItemImageTransactionHandler;
import com.simple_shop.item_service.item.storage.StorageClient;

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

	public RequestUploadUrlResponse requestUploadUrl(final long itemId) {
		/*
		 * TODO Request with image type / file ending, so that we can offer a matching
		 * path or reject an undesired type. Also with image type.
		 *
		 * TODO Make sure there are no collisions. This request could result in multiple
		 * item services from picking the same ID for the same item, even though it
		 * might be unlikely.
		 */
		final String uniqueId = UUID.randomUUID().toString();
		final String storageKey = "uploads/item-images/" + itemId + "/" + uniqueId + ".jpg";

		//
		final ItemImage pendingImage = new ItemImageBuilder()//
				.setItemId(itemId)//
				.setStorageKey(storageKey)//
				.setImageType(ImageType.THUMBNAIL) // TODO Replace with requested image type.
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
}
