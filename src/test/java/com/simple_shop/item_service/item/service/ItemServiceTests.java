package com.simple_shop.item_service.item.service;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.persistence.ItemTransactionHandler;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse.RequestResourcesResponseBuilder;
import com.simple_shop.item_service.item_image.service.ItemImageService;
import com.simple_shop.item_service.shared.exceptions.InvalidMessageException;
import com.simple_shop.item_service.utils.ItemUtils;

@ExtendWith(MockitoExtension.class)
public final class ItemServiceTests {
	@Mock
	private ItemTransactionHandler itemTransactionHandler;

	@Mock
	private ItemImageService itemImageService;

	@InjectMocks
	private ItemService itemService;

	public ItemServiceTests() {
		super();
	}

	@Test
	public void testGetItemById_ReturnsExistingItem() {
		final Item expectedItem = ItemUtils.createItem(42l, 10l);

		Mockito.when(this.itemTransactionHandler.findById(expectedItem.getId())).thenReturn(expectedItem);

		final Item actualItem = this.itemService.getItemById(expectedItem.getId());

		Assertions.assertThat(actualItem).isNotNull();
		Assertions.assertThat(actualItem).isEqualTo(expectedItem);
	}

	@Test
	public void testGetItemById_ThrowsWhenItemDoesntExist() {
		final long itemId = 42;

		Mockito.when(this.itemTransactionHandler.findById(itemId)).thenReturn(null);

		ItemServiceTests.assertThrowsWhenItemDoesntExist(() -> this.itemService.getItemById(itemId), itemId);
	}

	private static void assertThrowsWhenItemDoesntExist(final ThrowingCallable callable, final long itemId) {
		Assertions.assertThatThrownBy(callable)//
				.isInstanceOf(ItemNotFoundException.class)//
				.hasMessage("Didn't find item with ID " + itemId + "!");
	}

	@Test
	public void testGetItems() {
		final Pageable pageable = PageRequest.of(1, 10);
		final Page<Item> page = Page.empty();

		Mockito.when(this.itemTransactionHandler.findAll(pageable)).thenReturn(page);

		final Page<Item> actualPage = this.itemService.getItems(pageable);

		Assertions.assertThat(actualPage).isEqualTo(page);
	}

	@Test
	public void testCreateItem_Valid() {
		final Item item1 = ItemUtils.createItem(null, null);
		final Item item2 = ItemUtils.createItem(2l, null);
		final Item item3 = ItemUtils.createItem(3l, 10l);
		final long userId = 42;

		try (final MockedStatic<ItemTransactionHandler> handler = Mockito.mockStatic(ItemTransactionHandler.class)) {
			handler.when(() -> ItemTransactionHandler.finalizeItem(item1, userId)).thenReturn(item2);

			Mockito.when(this.itemTransactionHandler.saveItem(item2, ItemAction.CREATE)).thenReturn(item3);

			final Item actualCreatedItem = this.itemService.createItem(item1, userId);

			Assertions.assertThat(actualCreatedItem).isEqualTo(item3);
		}
	}

	@Test
	public void testCreateItem_Invalid() {
		final Item itemWithId = ItemUtils.createItem(1l, null);
		final Item itemWithVersion = ItemUtils.createItem(null, 1l);

		final long userId = 42;

		Assertions.assertThatThrownBy(() -> this.itemService.createItem(itemWithId, userId))//
				.isInstanceOf(InvalidMessageException.class)//
				.hasMessage("An item to create must not have an ID!");
		Assertions.assertThatThrownBy(() -> this.itemService.createItem(itemWithVersion, userId))//
				.isInstanceOf(InvalidMessageException.class)//
				.hasMessage("An item to create must not have a version!");
	}

	@Test
	public void testModifyItem_ReturnsModifiedItem() {
		final Item item1 = ItemUtils.createItem(1l, 11l);
		final Item item2 = ItemUtils.createItem(2l, 12l);
		final Item item3 = ItemUtils.createItem(13l, 13l);
		final long userId = 42;

		try (final MockedStatic<ItemTransactionHandler> handler = Mockito.mockStatic(ItemTransactionHandler.class)) {
			Mockito.when(this.itemTransactionHandler.findById(item1.getId())).thenReturn(item1);

			handler.when(() -> ItemTransactionHandler.finalizeItem(item1, userId)).thenReturn(item2);

			Mockito.when(this.itemTransactionHandler.saveItem(item2, ItemAction.MODIFY)).thenReturn(item3);

			final Item actualModifiedItem = this.itemService.modifyItem(item1, userId);

			Assertions.assertThat(actualModifiedItem).isEqualTo(item3);
		}
	}

	@Test
	public void testModifyItem_ThrowsWhenItemDoesntExist() {
		final Item item = ItemUtils.createItem(1l, 11l);
		final long userId = 42;

		Mockito.when(this.itemTransactionHandler.findById(item.getId())).thenReturn(null);

		ItemServiceTests.assertThrowsWhenItemDoesntExist(() -> this.itemService.modifyItem(item, userId), item.getId());
	}

	@Test
	public void testDeleteItem() {
		final Item item1 = ItemUtils.createItem(1l, 11l);
		final Item item2 = ItemUtils.createItem(2l, 12l);
		final long userId = 42;

		final RequestResourcesResponse requestResourcesResponse = new RequestResourcesResponseBuilder()//
				.setGalleryImageStorageKeys(List.of("key1", "key2"))//
				.setThumbnailImageStorageKey("thumbnailKey")//
				.build();

		Mockito.when(this.itemImageService.getImages(item1.getId())).thenReturn(requestResourcesResponse);
		Mockito.when(this.itemTransactionHandler.deleteItem(item1.getId(), userId)).thenReturn(item2);

		this.itemService.deleteItem(item1.getId(), userId);

		final InOrder inOrder = Mockito.inOrder(this.itemTransactionHandler, this.itemImageService);
		inOrder.verify(this.itemImageService).getImages(item1.getId());
		inOrder.verify(this.itemTransactionHandler).deleteItem(item1.getId(), userId);

		final List<String> storageKeys = new ArrayList<>();
		storageKeys.addAll(requestResourcesResponse.getGalleryImageStorageKeys());
		storageKeys.add(requestResourcesResponse.getThumbnailImageStorageKey());
		inOrder.verify(this.itemImageService).deleteImagesOnly(storageKeys);

		inOrder.verifyNoMoreInteractions();
	}
}