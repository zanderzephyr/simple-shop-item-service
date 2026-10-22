package com.simple_shop.item_service.item.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.service.ItemService;
import com.simple_shop.item_service.shared.dto.ServiceErrorResponseDto;
import com.simple_shop.item_service.shared.model.SortingDirection;
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

		this.executeInvalidTestWithTimestamp(() -> this.mockMvc.perform(//
				MockMvcRequestBuilders.get(ItemControllerTests.BASE_URL + "get-item/{itemId}", itemId)//
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isNotFound())//
				.andExpect(MockMvcResultMatchers.jsonPath("$.detail").value(exception.getMessage()))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpState").value(HttpStatus.NOT_FOUND.value()))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item not found"))//
				.andReturn()//
				.getResponse()//
				.getContentAsString());
	}

	@Test
	public void testGetItems_Success() throws Exception {
		final Item item = ItemUtils.createItem(1l, 11l);
		final Page<Item> page = new PageImpl<>(List.of(item));
		Mockito.when(this.itemService.getItems(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

		for (final SortingDirection sortingDirection : SortingDirection.values()) {
			this.mockMvc.perform(MockMvcRequestBuilders.get(ItemControllerTests.BASE_URL + "get-items")//
					.param("pageIndex", "0")//
					.param("pageSize", "10")//
					.param("sortingColumn", "Id")//
					.param("sortingDirection", sortingDirection.name())//
					.accept(MediaType.APPLICATION_JSON))//
					.andExpect(MockMvcResultMatchers.status().isOk())//
					.andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())//
					.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(item.getId().toString()));
		}
	}

	@Test
	public void testGetItems_InvalidRequest() throws Exception {
		this.executeInvalidTestWithTimestamp(() -> this.mockMvc.perform(//
				MockMvcRequestBuilders.get(ItemControllerTests.BASE_URL + "get-items")//
						.param("pageIndex", "0")//
						.param("pageSize", "10")//
						.param("sortingColumn", "Id")//
						.param("sortingDirection", "iAmNotASortingDirection")//
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())//
				// Not testing the detail field, as it this is not controller or advice content.
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpState").value(HttpStatus.BAD_REQUEST.value()))//
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid message!"))//
				.andReturn()//
				.getResponse()//
				.getContentAsString());
	}

	@Test
	public void testCreateItem_Success() throws Exception {
		final ItemDto incomingDto = new ItemDto()//
				.setDescription("A book about a little dude with hairy feet")//
				.setId(null)// must be null for items to create
				.setLastChange(null)//
				.setLastChangedBy(null)//
				.setMediaType(com.simple_shop.item_service.item.model.MediaType.BOOK.name())//
				.setPrice(999)//
				.setStock(3)//
				.setTitle("The Hobbit")//
				.setVersion(null)// must be null for items to create
		;
		final long executingUserId = 99;
		final Item incomingItem = DtoMapper.toDomain(incomingDto);
		final Item outgoingItem = ItemUtils.createItem(1l, 11l);
		final ItemDto outgoingDto = DtoMapper.toDto(outgoingItem);
		// The service serializes timestamps with ms precision. We compare this dto
		// later to the serialized object, therefore we simulate the serialization here.
		outgoingDto.setLastChange(outgoingDto.getLastChange().truncatedTo(ChronoUnit.MILLIS));

		Mockito.when(this.itemService.createItem(incomingItem, executingUserId)).thenReturn(outgoingItem);

		final String jsonResponse = this.mockMvc
				.perform(MockMvcRequestBuilders.post(ItemControllerTests.BASE_URL + "create-item")//
						.header("X-User-Id", Long.toString(executingUserId))//
						.contentType(MediaType.APPLICATION_JSON)//
						.content(this.objectMapper.writeValueAsString(incomingDto))//
						.accept(MediaType.APPLICATION_JSON))//
				.andExpect(MockMvcResultMatchers.status().isCreated())//
				.andReturn().getResponse()//
				.getContentAsString();

		final ItemDto actualOutgoingDto = this.objectMapper.readValue(jsonResponse, ItemDto.class);
		Assertions.assertThat(actualOutgoingDto).isEqualTo(outgoingDto);
	}

	private <THROWABLE extends Throwable> void executeInvalidTestWithTimestamp(
			final ThrowingSupplier<String, THROWABLE> jsonResponseSupplier) throws THROWABLE {
		final Instant beforeTest = Instant.now();

		final String jsonResponse = jsonResponseSupplier.get();

		final Instant afterTest = Instant.now();

		final ServiceErrorResponseDto errorResponse = this.objectMapper.readValue(jsonResponse,
				ServiceErrorResponseDto.class);
		Assertions.assertThat(errorResponse.getTimestamp())//
				.isNotNull()//
				.isBetween(beforeTest, afterTest);
	}

	// TODO Move to some lib
	@FunctionalInterface
	private static interface ThrowingSupplier<TYPE, THROWABLE extends Throwable> {
		TYPE get() throws THROWABLE;
	}
}
