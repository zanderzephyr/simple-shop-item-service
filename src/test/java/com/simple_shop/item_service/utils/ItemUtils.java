package com.simple_shop.item_service.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;

public class ItemUtils {
	private ItemUtils() {
		throw new UnsupportedOperationException();
	}

	public static Item createItem(final Long itemId, final Long version) {
		return new ItemBuilder()//
				.setDescription("an item")//
				.setId(itemId)//
				.setLastChange(LocalDateTime.now().minus(3, ChronoUnit.DAYS))//
				.setLastChangedBy(1l)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(999)//
				.setStock(99)//
				.setTitle("A cool book")//
				.setVersion(version)//
				.build();
	}
}
