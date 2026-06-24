package com.simple_shop.item_service.item.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.exceptions.ItemValidationException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;

/*
 * Proper version compatibility between frontend and backend is quite complex
 * and difficult to manage. For now, we simply leave the version segment as a
 * last resort if something needs a hard break in handling properly. For that
 * case, another controller with an incremented version could be created.
 *
 * TODO When interacting with the DB,
 *
 * - make sure SQL injection is impossible (prepared statements?).
 *
 * - make sure, the transaction wraps around Item and ItemHistory table
 * (combined)
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
		 * TODO Write item into the DB, add history entry (as one transaction with the
		 * Item insertion before), get the related entity object and translate it back
		 * into the response ItemDto.
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

	@PutMapping("/{id}")
	public ResponseEntity<ItemDto> modifyItem(@PathVariable final Integer id, @RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToModify = this.convert(itemDto);
		final Integer itemId = itemToModify.getId();
		if (itemId == null) {
			throw new ItemValidationException("No item ID!");
		}

		if (!itemId.equals(id)) {
			throw new ItemValidationException("Path ID and payload ID mismatch!");
		}

		/*
		 * TODO Update item in the DB (handle unknown item ID issue -> 404), add history
		 * entry (as one transaction with the Item update before), get the related
		 * entity object and translate it back into the response ItemDto. (Even though
		 * we might know the item state after modification for the initial rather simple
		 * DB schema, the schema could become more complex later, leading to an update
		 * with fairly complex consequences. Therefore, we should just always request
		 * the modified state from the DB before mirroring it back to the client.)
		 */

		final Item modifiedItem = ItemBuilder.initFrom(itemToModify)//
				.setLastChange(ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC))//
				// TODO Change to user ID from the session
				.setLastChangedBy(42)//
				.build();

		final ItemDto modifiedItemDto = this.convert(modifiedItem);

		return ResponseEntity.status(HttpStatus.OK).body(modifiedItemDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable final Integer id) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		/*
		 * TODO Delete item in the DB (handle unknown item ID issue -> 404) and add
		 * history entry (as one transaction with the Item deletion before).
		 */

		/*
		 * NO_CONTENT (204) means no response body. If we return OK (200), there would
		 * have to be a body.
		 */
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/*
	 * The sort string is the name of a member of the Item persistence class (i.e.
	 * of the entity class).
	 */
	@GetMapping
	public ResponseEntity<Page<ItemDto>> getAllItems(
			@PageableDefault(page = 0, size = 20, sort = "title", direction = Sort.Direction.ASC) final Pageable pageable) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		/*
		 * TODO Validate pageable object and return error if not valid. Don't allow
		 * sorting over any entity member that is inappropriate, i.e. hidden members,
		 * members that are mapped to unindexed columns, members with a lot of content
		 * (e.g. the imageUrl might be too long to sort).
		 */

		/*
		 * TODO Find items with the pageable object - for now, we hard return two items
		 * and ignore the pageable.
		 */

		// TODO Convert entities into ItemDto
		final List<ItemDto> pageItems = new ArrayList<>();
		final Item item1 = new ItemBuilder()//
				.setId(0)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC))//
				.setLastChangedBy(0)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(new BigDecimal("12.99"))//
				.setStock(3)//
				.setTitle("Harry Potter and the Philosopher's Stone")//
				.build();
		pageItems.add(this.convert(item1));
		final Item item2 = new ItemBuilder()//
				.setId(1)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC))//
				.setLastChangedBy(0)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(new BigDecimal("12.99"))//
				.setStock(3)//
				.setTitle("Harry Potter and the Chamber of Secrets")//
				.build();
		pageItems.add(this.convert(item2));

		/*
		 * TODO Hard-coded a number of 2 as the item count for now. Later, return the
		 * actual value.
		 */
		final Page<ItemDto> page = new PageImpl<>(pageItems, pageable, 2);

		return ResponseEntity.status(HttpStatus.OK).body(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemDto> getItem(@PathVariable final Integer id) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		/*
		 * TODO Request the requested item and handle unknown item ID issue -> 404.
		 * Translate the item back into the response ItemDto.
		 */

		final Item item = new ItemBuilder()//
				.setId(0)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC))//
				.setLastChangedBy(0)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(new BigDecimal("12.99"))//
				.setStock(3)//
				.setTitle("Harry Potter and the Philosopher's Stone")//
				.build();

		final ItemDto itemDto = this.convert(item);

		return ResponseEntity.status(HttpStatus.OK).body(itemDto);
	}

	@ExceptionHandler(ItemValidationException.class)
	public ResponseEntity<Map<String, Object>> handleItemValidationException(final ItemValidationException exception) {
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

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleItemNotFoundException(final ItemNotFoundException exception) {
		final Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", Instant.now());
		errorBody.put("status", HttpStatus.NOT_FOUND.value());
		errorBody.put("error", "Item not found");
		errorBody.put("message", exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
	}

	private Item convert(final ItemDto itemDto) {
		return new ItemBuilder()//
				.setId(itemDto.getId())//
				.setImageUrl(itemDto.getImageUrl())//
				.setLastChange(itemDto.getLastChange() == null ? null : itemDto.getLastChange().atZone(ZoneOffset.UTC))//
				.setLastChangedBy(itemDto.getLastChangedBy())//
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