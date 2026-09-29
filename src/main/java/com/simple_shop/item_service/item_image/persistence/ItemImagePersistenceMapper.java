package com.simple_shop.item_service.item_image.persistence;

import com.simple_shop.item_service.item_image.model.ItemImage;
import com.simple_shop.item_service.item_image.model.ItemImage.ItemImageBuilder;

public final class ItemImagePersistenceMapper {
	private ItemImagePersistenceMapper() {
		throw new UnsupportedOperationException();
	}

	public static ItemImage toDomain(final ItemImageEntity itemImageEntity) {
		return new ItemImageBuilder()//
				.setCreatedAt(itemImageEntity.getCreatedAt())//
				.setId(itemImageEntity.getId()) //
				.setImageState(itemImageEntity.getImageState()) //
				.setImageType(itemImageEntity.getImageType()) //
				.setImageFileType(itemImageEntity.getImageFileType()) //
				.setItemId(itemImageEntity.getItemId()) //
				.setStorageKey(itemImageEntity.getStorageKey()) //
				.build();
	}

	public static ItemImageEntity toEntity(final ItemImage itemImage) {
		final ItemImageEntity entity = new ItemImageEntity();

		entity.setCreatedAt(itemImage.getCreatedAt());
		entity.setId(itemImage.getId());
		entity.setImageState(itemImage.getImageState());
		entity.setImageType(itemImage.getImageType());
		entity.setImageFileType(itemImage.getImageFileType());
		entity.setItemId(itemImage.getItemId());
		entity.setStorageKey(itemImage.getStorageKey());

		return entity;
	}
}
