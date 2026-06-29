package com.simple_shop.item_service.item.controller;

import java.time.ZoneOffset;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;

public final class ItemDtoMapper {
	private ItemDtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static Item toDomain(final ItemDto itemDto) {
		return new ItemBuilder()//
				.setId(itemDto.getId())//
				.setImageUrl(itemDto.getImageUrl())//
				.setLastChange(itemDto.getLastChange() == null ? null
						: itemDto.getLastChange().atZone(ZoneOffset.UTC).toLocalDateTime())//
				.setLastChangedBy(itemDto.getLastChangedBy())//
				.setMediaType(itemDto.getMediaType())//
				.setPrice(itemDto.getPrice())//
				.setStock(itemDto.getStock())//
				.setTitle(itemDto.getTitle())//
				.setVersion(itemDto.getVersion())//
				.build();
	}

	public static ItemDto toDto(final Item item) {
		final ItemDto itemDto = new ItemDto();

		itemDto.setId(item.getId());
		itemDto.setImageUrl(item.getImageUrl());
		itemDto.setLastChange(item.getLastChange().toInstant(ZoneOffset.UTC));
		itemDto.setLastChangedBy(item.getLastChangedBy());
		itemDto.setMediaType(item.getMediaType());
		itemDto.setPrice(item.getPrice());
		itemDto.setStock(item.getStock());
		itemDto.setTitle(item.getTitle());
		itemDto.setVersion(item.getVersion());

		return itemDto;
	}
}
