package com.simple_shop.item_service.item.controller;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.controller.ItemRequestConstraints.SortingDirection;
import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.dto.ItemDto.ItemDtoMember;
import com.simple_shop.item_service.item.exceptions.InvalidItemRequestConstraintsException;
import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.exceptions.ItemValidationException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;
import com.simple_shop.item_service.item.persistence.ItemEntity.ItemEntityMember;

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
	private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

	private static final long MAX_PAGE_SIZE = 100;

	private static final Map<String, String> SORTABLE_DTO_TO_ENTITY_COLUMNS = ItemController.collectSortableColumns();

	private static Map<String, String> collectSortableColumns() {
		final Map<String, String> columns = new HashMap<>();

		columns.put(ItemDtoMember.LAST_CHANGE.getDomainName(), ItemEntityMember.LAST_CHANGE.getDomainName());
		columns.put(ItemDtoMember.LAST_CHANGED_BY.getDomainName(), ItemEntityMember.LAST_CHANGED_BY.getDomainName());
		columns.put(ItemDtoMember.MEDIA_TYPE.getDomainName(), ItemEntityMember.MEDIA_TYPE.getDomainName());
		columns.put(ItemDtoMember.PRICE.getDomainName(), ItemEntityMember.PRICE.getDomainName());
		columns.put(ItemDtoMember.STOCK.getDomainName(), ItemEntityMember.STOCK.getDomainName());
		columns.put(ItemDtoMember.TITLE.getDomainName(), ItemEntityMember.TITLE.getDomainName());

		return columns;
	}

	public ItemController() {
		super();
	}

	@PostMapping
	public ResponseEntity<ItemDto> createItem(@RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToCreate = ItemDtoMapper.toDomain(itemDto);

		/*
		 * TODO Write item into the DB, add history entry (as one transaction with the
		 * Item insertion before), get the related entity object and translate it back
		 * into the response ItemDto.
		 */

		final Item createdItem = ItemBuilder.initFrom(itemToCreate)//
				// TODO Take from the DB entity later
				.setId(ItemController.ID_GENERATOR.getAndIncrement())//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())//
				// TODO Change to user ID from the session
				.setLastChangedBy(42l)//
				.build();

		final ItemDto createdItemDto = ItemDtoMapper.toDto(createdItem);

		return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ItemDto> modifyItem(@PathVariable final Long id, @RequestBody final ItemDto itemDto) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		final Item itemToModify = ItemDtoMapper.toDomain(itemDto);
		final Long itemId = itemToModify.getId();
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
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())//
				// TODO Change to user ID from the session
				.setLastChangedBy(42l)//
				.build();

		final ItemDto modifiedItemDto = ItemDtoMapper.toDto(modifiedItem);

		return ResponseEntity.status(HttpStatus.OK).body(modifiedItemDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteItem(@PathVariable final Long id) {
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

	// Using ModelAttribute to prevent GET requests without body causing issues
	@GetMapping
	public ResponseEntity<Page<ItemDto>> getAllItems(@ModelAttribute final ItemRequestConstraints constraints) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		this.validate(constraints);

		// TODO Convert constraints to Pageable

		/*
		 * TODO Find items with the pageable object - for now, we hard return two items
		 * and ignore the pageable.
		 */

		// TODO Convert entities into ItemDto
		final List<ItemDto> pageItems = new ArrayList<>();
		final Item item1 = new ItemBuilder()//
				.setId(1l)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())//
				.setLastChangedBy(1l)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(1299)//
				.setStock(3)//
				.setTitle("Harry Potter and the Philosopher's Stone")//
				.build();
		pageItems.add(ItemDtoMapper.toDto(item1));
		final Item item2 = new ItemBuilder()//
				.setId(1l)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())//
				.setLastChangedBy(1l)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(1299)//
				.setStock(3)//
				.setTitle("Harry Potter and the Chamber of Secrets")//
				.build();
		pageItems.add(ItemDtoMapper.toDto(item2));

		final Pageable pageable = this.createPageable(constraints);
		/*
		 * TODO Hard-coded a number of 2 as the item count for now. Later, return the
		 * actual value.
		 */
		final Page<ItemDto> page = new PageImpl<>(pageItems, pageable, 2);

		return ResponseEntity.status(HttpStatus.OK).body(page);
	}

	private Pageable createPageable(final ItemRequestConstraints constraints) {
		final SortingDirection sortingDirection = constraints.getSortingDirection();
		final Direction direction;
		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new IllegalStateException("Unexpected sorting direction: " + sortingDirection);
		}
		final String sortingColumnEntity = ItemController.SORTABLE_DTO_TO_ENTITY_COLUMNS
				.get(constraints.getSortingColumn());
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));

		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	private void validate(final ItemRequestConstraints constraints) throws InvalidItemRequestConstraintsException {
		if (constraints == null) {
			throw new InvalidItemRequestConstraintsException("No item search constraints!");
		}

		final int pageIndex = constraints.getPageIndex();
		if (pageIndex < 0) {
			throw new InvalidItemRequestConstraintsException("Page index must not be negative, but was " + pageIndex);
		}

		final int pageSize = constraints.getPageSize();
		if (pageSize <= 0 || pageSize > ItemController.MAX_PAGE_SIZE) {
			throw new InvalidItemRequestConstraintsException(
					"Page size must be in [1," + ItemController.MAX_PAGE_SIZE + "]!");
		}

		final String sortingColumn = constraints.getSortingColumn();
		if (!ItemController.SORTABLE_DTO_TO_ENTITY_COLUMNS.keySet().contains(sortingColumn)) {
			throw new InvalidItemRequestConstraintsException(
					"Columns " + sortingColumn + " is not allowed for sorting!");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemDto> getItem(@PathVariable final Long id) {
		/*
		 * TODO Validate session is ok. If it isn't, answer appropriately with negative
		 * response.
		 */

		/*
		 * TODO Request the requested item and handle unknown item ID issue -> 404.
		 * Translate the item back into the response ItemDto.
		 */

		final Item item = new ItemBuilder()//
				.setId(1l)//
				.setImageUrl("www.bla.com")//
				.setLastChange(ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime())//
				.setLastChangedBy(1l)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(1299)//
				.setStock(3)//
				.setTitle("Harry Potter and the Philosopher's Stone")//
				.build();

		final ItemDto itemDto = ItemDtoMapper.toDto(item);

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

	@ExceptionHandler(InvalidItemRequestConstraintsException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidItemRequestConstraintsException(
			final InvalidItemRequestConstraintsException exception) {
		final Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", Instant.now());
		errorBody.put("status", HttpStatus.BAD_REQUEST.value());
		errorBody.put("error", "Page setup is invalid");
		errorBody.put("message", exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<Map<String, Object>> handleIllegalStateException(final IllegalStateException exception) {
		final Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("timestamp", Instant.now());
		errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		errorBody.put("error", "Internal server error");
		errorBody.put("message", exception.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
	}
}