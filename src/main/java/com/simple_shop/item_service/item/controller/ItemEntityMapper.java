package com.simple_shop.item_service.item.controller;

import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.ItemHistory;
import com.simple_shop.item_service.item.model.ItemHistory.ItemHistoryBuilder;
import com.simple_shop.item_service.item.persistence.ItemEntity;
import com.simple_shop.item_service.item.persistence.ItemHistoryEntity;

public final class ItemEntityMapper {
	private ItemEntityMapper() {
		throw new UnsupportedOperationException();
	}

	public static Item toDomain(final ItemEntity itemEntity) {
		return new ItemBuilder()//
				.setId(itemEntity.getId()) //
				.setImageUrl(itemEntity.getImageUrl()) //
				.setLastChange(itemEntity.getLastChange()) //
				.setLastChangedBy(itemEntity.getLastChangedBy()) //
				.setMediaType(itemEntity.getMediaType())//
				.setPrice(itemEntity.getPrice())//
				.setStock(itemEntity.getStock())//
				.setTitle(itemEntity.getTitle())//
				.build();
	}

	public static ItemEntity toEntity(final Item item) {
		final ItemEntity entity = new ItemEntity();

		entity.setId(item.getId());
		entity.setImageUrl(item.getImageUrl());
		entity.setLastChange(item.getLastChange());
		entity.setLastChangedBy(item.getLastChangedBy());
		entity.setMediaType(item.getMediaType());
		entity.setPrice(item.getPrice());
		entity.setStock(item.getStock());
		entity.setTitle(item.getTitle());

		return entity;
	}

	public static ItemHistory toDomain(final ItemHistoryEntity itemHistoryEntity) {
		return new ItemHistoryBuilder()//
				.setAction(itemHistoryEntity.getAction())//
				.setId(itemHistoryEntity.getId()) //
				.setItemId(itemHistoryEntity.getItemId()) //
				.setImageUrl(itemHistoryEntity.getImageUrl()) //
				.setLastChange(itemHistoryEntity.getLastChange()) //
				.setLastChangedBy(itemHistoryEntity.getLastChangedBy()) //
				.setMediaType(itemHistoryEntity.getMediaType())//
				.setPrice(itemHistoryEntity.getPrice())//
				.setStock(itemHistoryEntity.getStock())//
				.setTitle(itemHistoryEntity.getTitle())//
				.build();
	}

	public static ItemHistoryEntity toEntity(final ItemHistory itemHistory) {
		final ItemHistoryEntity entity = new ItemHistoryEntity();

		entity.setAction(itemHistory.getAction());
		entity.setId(itemHistory.getId());
		entity.setItemId(itemHistory.getItemId());
		entity.setImageUrl(itemHistory.getImageUrl());
		entity.setLastChange(itemHistory.getLastChange());
		entity.setLastChangedBy(itemHistory.getLastChangedBy());
		entity.setMediaType(itemHistory.getMediaType());
		entity.setPrice(itemHistory.getPrice());
		entity.setStock(itemHistory.getStock());
		entity.setTitle(itemHistory.getTitle());

		return entity;
	}
}
