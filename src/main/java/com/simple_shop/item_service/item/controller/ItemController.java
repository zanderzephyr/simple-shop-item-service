package com.simple_shop.item_service.item.controller;

import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.dto.RequestConstraintsDto;
import com.simple_shop.item_service.item.exceptions.InvalidMessageException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.RequestConstraints;
import com.simple_shop.item_service.item.model.SortingDirection;
import com.simple_shop.item_service.item.service.ItemService;

@RestController
@RequestMapping("/api/items/v1/regular")
public final class ItemController {
	private final ItemService itemService;

	public ItemController(final ItemService itemService) {
		super();

		this.itemService = itemService;
	}

	@PostMapping
	public ResponseEntity<ItemDto> createItem(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@RequestBody final ItemDto itemDto) {

		if (itemDto == null) {
			throw new InvalidMessageException("No item!");
		}

		final Item itemToCreate = DtoMapper.toDomain(itemDto);

		final Item createdItem = this.itemService.createItem(itemToCreate, executingUserId);
		final ItemDto createdItemDto = DtoMapper.toDto(createdItem);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdItemDto);
	}

	@PutMapping("/{itemId}")
	public ResponseEntity<ItemDto> modifyItem(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@PathVariable final long itemId, //
			@RequestBody final ItemDto itemDto) {

		if (itemDto == null) {
			throw new InvalidMessageException("No item!");
		}

		final Item itemToModify = DtoMapper.toDomain(itemDto);
		if (!Long.valueOf(itemId).equals(itemToModify.getId())) {
			throw new InvalidMessageException("Path ID and payload ID mismatch!");
		}
		if (itemDto.getVersion() == null) {
			throw new InvalidMessageException("Version must not be null!");
		}

		final Item modifiedItem = this.itemService.modifyItem(itemToModify, executingUserId);
		final ItemDto modifiedItemDto = DtoMapper.toDto(modifiedItem);
		return ResponseEntity.status(HttpStatus.OK).body(modifiedItemDto);
	}

	/*
	 * No version checking here for optimistic locking. A delete operation is valid
	 * even on stale data.
	 */
	@DeleteMapping("/{itemId}")
	public ResponseEntity<Void> deleteItem(//
			@RequestHeader(name = "X-User-Id") final long executingUserId, //
			@PathVariable final long itemId) {

		this.itemService.deleteItem(itemId, executingUserId);

		/*
		 * NO_CONTENT (204) means no response body. If we return OK (200), there would
		 * have to be a body.
		 */
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// Using ModelAttribute to prevent GET requests without body causing issues
	@GetMapping
	public ResponseEntity<PagedModel<ItemDto>> getItems(
			@ModelAttribute final RequestConstraintsDto requestConstraintsDto) {

		if (requestConstraintsDto == null) {
			throw new InvalidMessageException("No request constraints!");
		}

		final RequestConstraints requestConstraints = DtoMapper.toDomain(requestConstraintsDto);

		final Pageable pageable = this.createPageable(requestConstraints);
		final Page<ItemDto> page = this.itemService.getItems(pageable).map(DtoMapper::toDto);
		final PagedModel<ItemDto> pagedModel = new PagedModel<>(page);
		return ResponseEntity.status(HttpStatus.OK).body(pagedModel);
	}

	// TODO Move to common lib
	private Pageable createPageable(final RequestConstraints constraints) {
		final Direction direction = ItemController.convert(constraints.getSortingDirection());
		final String sortingColumnEntity = constraints.getSortingColumn();
		final Sort sort = Sort.by(Arrays.asList(new Order(direction, sortingColumnEntity)));
		return PageRequest.of(constraints.getPageIndex(), constraints.getPageSize(), sort);
	}

	// TODO Move to common lib
	private static Direction convert(final SortingDirection sortingDirection) {
		final Direction direction;

		if (SortingDirection.ASCENDING.equals(sortingDirection)) {
			direction = Direction.ASC;
		} else if (SortingDirection.DESCENDING.equals(sortingDirection)) {
			direction = Direction.DESC;
		} else {
			throw new InvalidMessageException("Unexpected sorting direction: " + sortingDirection);
		}

		return direction;
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<ItemDto> getItem(@PathVariable final long itemId) {
		final Item item = this.itemService.getItemById(itemId);
		final ItemDto itemDto = DtoMapper.toDto(item);
		return ResponseEntity.status(HttpStatus.OK).body(itemDto);
	}
}