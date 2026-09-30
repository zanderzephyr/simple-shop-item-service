package com.simple_shop.item_service.item_image.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.item_service.item_image.model.ImageState;
import com.simple_shop.item_service.item_image.persistence.ItemImageEntity;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImageEntity, Long> {
	/*
	 * The unique constraint of the storageKey column causes an index, which makes
	 * this access fast. No need to use item ID and image state index.
	 */
	Optional<ItemImageEntity> findByStorageKey(final String storageKey);

	void deleteByStorageKey(final String storageKey);

	void deleteByStorageKeyIn(final List<String> storageKeys);

	/*
	 * Leverages the the SQL index INDEX_ItemImage_ItemId_ImageState to be quick.
	 */
	List<ItemImageEntity> findByItemIdAndImageState(final long itemId, final ImageState imageState);

	List<ItemImageEntity> findByImageStateAndCreatedAtBefore(//
			final ImageState imageState, final LocalDateTime threshold);
}
