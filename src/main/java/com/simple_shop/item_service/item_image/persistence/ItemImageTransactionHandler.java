package com.simple_shop.item_service.item_image.persistence;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.item_service.item_image.model.ItemImage;
import com.simple_shop.item_service.item_image.repository.ItemImageRepository;

@Component
public class ItemImageTransactionHandler {
	private final ItemImageRepository itemImageRepository;

	public ItemImageTransactionHandler(final ItemImageRepository itemImageRepository) {
		super();

		this.itemImageRepository = itemImageRepository;
	}

	@Transactional
	public ItemImage saveItemImage(final ItemImage itemImage) {
		final ItemImageEntity itemImageEntity = ItemImagePersistenceMapper.toEntity(itemImage);
		final ItemImageEntity savedItemImageEntity = this.itemImageRepository.save(itemImageEntity);
		return ItemImagePersistenceMapper.toDomain(savedItemImageEntity);
	}

	@Transactional(readOnly = true)
	public ItemImage findByStorageKey(final String storageKey) {
		final ItemImageEntity entity = this.itemImageRepository.findByStorageKey(storageKey).orElse(null);
		return entity == null ? null : ItemImagePersistenceMapper.toDomain(entity);
	}
}
