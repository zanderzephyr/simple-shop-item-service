package com.simple_shop.item_service.item.controller;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.exceptions.InvalidRequestException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;
import com.simple_shop.item_service.utils.DataTypeUtils;

public final class DtoMapper {
	private DtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static Item toDomain(final ItemDto itemDto) {
		try {
			return new ItemBuilder()//
					.setId(itemDto.getId())//
					.setImageUrl(itemDto.getImageUrl())//
					.setMediaType(DataTypeUtils.parseEnum(itemDto.getMediaType(), MediaType.class))//
					.setPrice(itemDto.getPrice())//
					.setStock(itemDto.getStock())//
					.setTitle(itemDto.getTitle())//
					.setLastChange(itemDto.getLastChange())//
					.setLastChangedBy(itemDto.getLastChangedBy())//
					.setVersion(itemDto.getVersion())//
					.build();
		} catch (final Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	/*
	 * This is the location to set the precision of decimals, should we ever have
	 * them and need precision. Do not solve this using the formatters.
	 */
	public static ItemDto toDto(final Item item) {
		final ItemDto itemDto = new ItemDto();

		itemDto.setId(item.getId());
		itemDto.setImageUrl(item.getImageUrl());
		itemDto.setMediaType(item.getMediaType().name());
		itemDto.setPrice(item.getPrice());
		itemDto.setStock(item.getStock());
		itemDto.setTitle(item.getTitle());
		itemDto.setLastChange(item.getLastChange());
		itemDto.setLastChangedBy(item.getLastChangedBy());
		itemDto.setVersion(item.getVersion());

		return itemDto;
	}
}
