package com.simple_shop.item_service.item.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;
import com.simple_shop.item_service.item.persistence.ItemTransactionHandler;
import com.simple_shop.item_service.item_image.service.ItemImageService;

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
	public void getItemById_ShouldReturnExistingItem() {
		final Item expectedItem = this.createItem();

		Mockito.when(this.itemTransactionHandler.findById(expectedItem.getId())).thenReturn(expectedItem);

		final Item actualItem = this.itemService.getItemById(expectedItem.getId());

		Assertions.assertThat(actualItem).isNotNull();
		Assertions.assertThat(actualItem).isEqualTo(expectedItem);
	}

	@Test
	public void getItemById_ShouldThrowWhenItemDoesntExist() {
		final long id = 42;

		Mockito.when(this.itemTransactionHandler.findById(id)).thenReturn(null);

		Assertions.assertThatThrownBy(() -> this.itemService.getItemById(id))//
				.isInstanceOf(ItemNotFoundException.class)//
				.hasMessage("Didn't find item with ID " + id + "!");
	}

	private Item createItem() {
		return new ItemBuilder()//
				.setDescription("an item")//
				.setId(42l)//
				.setLastChange(LocalDateTime.now().minus(3, ChronoUnit.DAYS))//
				.setLastChangedBy(1l)//
				.setMediaType(MediaType.BOOK)//
				.setPrice(999)//
				.setStock(99)//
				.setTitle("A cool book")//
				.setVersion(10l)//
				.build();
	}
}