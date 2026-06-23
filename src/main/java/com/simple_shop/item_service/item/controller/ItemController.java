package com.simple_shop.item_service.item.controller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;

/*
 * Proper version compatibility between frontend and backend is quite complex
 * and difficult to manage. For now, we simply leave the version segment as a
 * last resort if something needs a hard break in handling properly. For that
 * case, another controller with an incremented version could be created.
 */
@RestController
@RequestMapping("/api/v1/items")
public final class ItemController {
	// Just for initial scaffolding
	private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

	public ItemController() {
		super();
	}

	@PostMapping
	public ResponseEntity<ItemDto> createItem(@RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToCreate = this.convert(itemDto);

		/*
		 * TODO Write item into the DB, get the related entity object and translate it
		 * back into the response ItemDto.
		 */

		final Item createdItem = ItemBuilder.initFrom(itemToCreate)//
				// TODO Take from the DB entity later
				.setId(ItemController.ID_GENERATOR.getAndIncrement())//
				.setLastChange(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC))//
				// TODO Change to user ID from the session
				.setLastChangedBy(42)//
				.build();

		final ItemDto createdItemDto = this.convert(createdItem);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDto);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, Object>> handleItemValidationException(final IllegalArgumentException exception) {
		final Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", Instant.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Item validation Failed");
		errorBody.put("message", exception.getMessage());
		/*
		 * The map is simply converted to a JSON representation by Spring transparently.
		 * Each data type has a formatter defined in Spring. If we use our own types, we
		 * need to make sure, they are properly formatted to be contained in the JSON
		 * representation, i.e. it has to be a valid JSON value with valid value type.
		 */
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
	}

	private Item convert(final ItemDto itemDto) {
		return new ItemBuilder()//
				.setImageUrl(itemDto.getImageUrl())//
				.setMediaType(itemDto.getMediaType())//
				.setPrice(itemDto.getPrice())//
				.setStock(itemDto.getStock())//
				.setTitle(itemDto.getTitle())//
				.build();
	}

	private ItemDto convert(final Item item) {
		final ItemDto itemDto = new ItemDto();

		itemDto.setId(item.getId());
		itemDto.setImageUrl(item.getImageUrl());
		itemDto.setLastChange(item.getLastChange().toInstant());
		itemDto.setLastChangedBy(item.getLastChangedBy());
		itemDto.setMediaType(item.getMediaType());
		itemDto.setPrice(item.getPrice());
		itemDto.setStock(item.getStock());
		itemDto.setTitle(item.getTitle());

		return itemDto;
	}
}