package com.simple_shop.item_service.item.controller;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.service.ItemService;
import com.simple_shop.item_service.shared.dto.ServiceErrorResponseDto;
import com.simple_shop.item_service.utils.ItemUtils;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ItemController.class)
public class ItemControllerTests {
	private static final String BASE_URL = "/api/items/v1/regular/";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockitoBean
	private ItemService itemService;

	public ItemControllerTests() {
		super();
	}

	@Test
	public void testGetItem_Success() throws Exception {
		final Item item = ItemUtils.createItem(1l, 11l);

		Mockito.when(this.itemService.getItemById(item.getId())).thenReturn(item);

		this.mockMvc
				.perform(MockMvcRequestBuilders.get(ItemControllerTests.BASE_URL + "get-item/{itemId}", item.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId().toString()));
	}

	@Test
	public void testGetItem_ItemDoesntExist() throws Exception {
		final long itemId = 1;

		final ItemNotFoundException exception = new ItemNotFoundException("Didn't find item with ID " + itemId + "!");
		Mockito.when(this.itemService.getItemById(itemId)).thenThrow(exception);

		final Instant beforeTest = Instant.now();

		final String jsonResponse = this.mockMvc.perform(//
				MockMvcRequestBuilders.get(ItemControllerTests.BASE_URL + "get-item/{itemId}", itemId)//
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())//
				.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value(exception.getMessage()))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpState").value(HttpStatus.NOT_FOUND.value()))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item not found"))//
				.andReturn()//
				.getResponse()//
				.getContentAsString();

		final Instant afterTest = Instant.now();

		final ServiceErrorResponseDto errorResponse = this.objectMapper.readValue(jsonResponse,
				ServiceErrorResponseDto.class);
		Assertions.assertThat(errorResponse.getTimestamp())//
				.isNotNull()//
				.isBetween(beforeTest, afterTest);
	}
}
