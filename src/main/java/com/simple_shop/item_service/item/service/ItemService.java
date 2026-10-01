package com.simple_shop.item_service.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.persistence.ItemTransactionHandler;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse;
import com.simple_shop.item_service.item_image.service.ItemImageService;
import com.simple_shop.item_service.shared.exceptions.InvalidMessageException;

@Service
public final class ItemService {
	private final ItemTransactionHandler itemTransactionHandler;

	private final ItemImageService itemImageService;

	public ItemService(//
			final ItemTransactionHandler itemTransactionHandler, //
			final ItemImageService itemImageService) {

		super();

		this.itemTransactionHandler = itemTransactionHandler;
		this.itemImageService = itemImageService;
	}

	public Page<Item> getItems(final Pageable pageable) {
		return this.itemTransactionHandler.findAll(pageable);
	}

	public Item getItemById(final long id) {
		final Item item = this.findItemById(id);
		return item;
	}

	public Item createItem(final Item itemToCreate, final long userId) {
		ItemService.validateItemToCreate(itemToCreate);

		final Item finalizedItem = ItemTransactionHandler.finalizeItem(itemToCreate, userId);
		final Item createdItem = this.itemTransactionHandler.saveItem(finalizedItem, ItemAction.CREATE);
		return createdItem;
	}

	private static void validateItemToCreate(final Item itemToCreate) {
		if (itemToCreate.getId() != null) {
			throw new InvalidMessageException("An item to create must not have an ID!");
		}

		if (itemToCreate.getVersion() != null) {
			throw new InvalidMessageException("An item to create must not have a version!");
		}
	}

	public Item modifyItem(final Item itemToModify, final long userId) {
		// Ensure the item exists
		this.findItemById(itemToModify.getId());

		final Item finalizedItem = ItemTransactionHandler.finalizeItem(itemToModify, userId);

		final Item modifiedItem = this.itemTransactionHandler.saveItem(finalizedItem, ItemAction.MODIFY);

		return modifiedItem;
	}

	/*
	 * The order of item and image deletion is important. Careful when working here.
	 */
	public void deleteItem(final long itemId, final long userId) {
		final RequestResourcesResponse itemImageResponse = this.itemImageService.getImages(itemId);

		this.deleteItemFromDb(itemId, userId);

		this.itemImageService.deleteImagesOnly(itemImageResponse.getGalleryImageStorageKeys());
	}

	private void deleteItemFromDb(final long itemId, final long userId) {
		final Item item = this.itemTransactionHandler.deleteItem(itemId, userId);
		if (item == null) {
			throw new ItemNotFoundException(ItemService.createItemNotFoundMessage(itemId));
		}
	}

	private Item findItemById(final long itemId) {
		final Item item = this.itemTransactionHandler.findById(itemId);
		if (item == null) {
			throw new ItemNotFoundException(ItemService.createItemNotFoundMessage(itemId));
		}
		return item;
	}

	private static String createItemNotFoundMessage(final long itemId) {
		return "Didn't find item with ID " + itemId + "!";
	}
}
