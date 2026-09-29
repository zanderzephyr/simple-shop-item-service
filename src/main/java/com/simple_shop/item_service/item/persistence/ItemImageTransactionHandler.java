package com.simple_shop.item_service.item.persistence;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.item_service.item.model.ItemImage;
import com.simple_shop.item_service.item.repository.ItemImageRepository;

@Component
public class ItemImageTransactionHandler {
	private final ItemImageRepository itemImageRepository;

	public ItemImageTransactionHandler(final ItemImageRepository itemImageRepository) {
		super();

		this.itemImageRepository = itemImageRepository;
	}

	@Transactional
	public ItemImage saveItemImage(final ItemImage itemImage) {
		final ItemImageEntity itemImageEntity = PersistenceMapper.toEntity(itemImage);
		final ItemImageEntity savedItemImageEntity = this.itemImageRepository.save(itemImageEntity);
		return PersistenceMapper.toDomain(savedItemImageEntity);
	}

	@Transactional(readOnly = true)
	public ItemImage findByStorageKey(final String storageKey) {
		final ItemImageEntity entity = this.itemImageRepository.findByStorageKey(storageKey).orElse(null);
		return entity == null ? null : PersistenceMapper.toDomain(entity);
	}
}
