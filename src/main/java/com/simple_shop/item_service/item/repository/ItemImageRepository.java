package com.simple_shop.item_service.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simple_shop.item_service.item.persistence.ItemImageEntity;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImageEntity, Long> {
	// Spring Data JPA automatically provides standard CRUD and Paging methods.
}
