package com.simple_shop.item_service.item_image.persistence;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.item_service.item_image.model.ImageState;
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

	@Transactional
	public void deleteByStorageKey(final String storageKey) {
		this.itemImageRepository.deleteByStorageKey(storageKey);
	}

	@Transactional(readOnly = true)
	public List<ItemImage> findByItemIdAndImageState(final long itemId, final ImageState imageState) {
		return this.itemImageRepository.findByItemIdAndImageState(itemId, imageState).stream()//
				.map(ItemImagePersistenceMapper::toDomain)//
				.toList();
	}
}
