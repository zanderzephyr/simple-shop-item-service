package com.simple_shop.item_service.item_image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.item_service.item_image.persistence.ItemImageEntity;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImageEntity, Long> {
	/*
	 * The unique constraint of the storageKey column causes an index, which makes
	 * this access fast. No need to use item ID and image state index.
	 */
	Optional<ItemImageEntity> findByStorageKey(final String storageKey);

	void deleteByStorageKey(final String storageKey);
}
