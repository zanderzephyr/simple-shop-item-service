package com.simple_shop.item_service.item.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simple_shop.item_service.item.controller.ItemEntityMapper;
import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.model.ItemHistoryState;
import com.simple_shop.item_service.item.model.ItemHistoryState.ItemHistoryStateBuilder;
import com.simple_shop.item_service.item.persistence.ItemEntity;
import com.simple_shop.item_service.item.persistence.ItemHistoryStateEntity;
import com.simple_shop.item_service.item.repository.ItemEntityRepository;
import com.simple_shop.item_service.item.repository.ItemHistoryStateEntityRepository;
import com.simple_shop.item_service.utils.ItemServiceUtils;

@Service
public class ItemService {
	private final ItemEntityRepository itemRepository;

	private final ItemHistoryStateEntityRepository itemHistoryStateRepository;

	public ItemService(final ItemEntityRepository itemRepository,
			final ItemHistoryStateEntityRepository historyRepository) {
		super();

		this.itemRepository = itemRepository;
		this.itemHistoryStateRepository = historyRepository;
	}

	@Transactional(readOnly = true)
	public Page<Item> getAllItems(final Pageable pageable) {
		final Page<ItemEntity> entityPage = this.itemRepository.findAll(pageable);

		return entityPage.map(ItemEntityMapper::toDomain);
	}

	@Transactional(readOnly = true)
	public Item getItemById(final long id) {
		return this.itemRepository.findById(id) //
				.map(ItemEntityMapper::toDomain) //
				.orElseThrow(() -> new ItemNotFoundException("Didn't find item with ID " + id + "!"));
	}

	@Transactional
	public Item createItem(final Item itemToCreate, final long userId) {
		final Item finalizedItem = ItemBuilder.initFrom(itemToCreate) //
				.setId(null) // Just making sure in case a client sends an ID inappropriately
				.setLastChange(ItemServiceUtils.createNow()) //
				.setLastChangedBy(userId) //
				.build();
		final ItemEntity finalizedItemEntity = ItemEntityMapper.toEntity(finalizedItem);

		final ItemEntity createdItemEntity = this.itemRepository.save(finalizedItemEntity);
		final Item createdItem = ItemEntityMapper.toDomain(createdItemEntity);

		final ItemHistoryState itemHistoryState = this.buildItemHistoryState(createdItem, ItemAction.CREATE);
		final ItemHistoryStateEntity itemHistoryStateEntity = ItemEntityMapper.toEntity(itemHistoryState);
		this.itemHistoryStateRepository.save(itemHistoryStateEntity);

		return createdItem;
	}

	@Transactional
	public Item modifyItem(final Item itemToModify, final long userId) {
		// Ensure the item exists
		this.itemRepository.findById(itemToModify.getId()) //
				.orElseThrow(() -> new ItemNotFoundException(
						"Cannot modify item. Item " + itemToModify.getId() + " not found!"));

		final Item finalizedItem = ItemBuilder.initFrom(itemToModify) //
				.setLastChange(ItemServiceUtils.createNow()) //
				.setLastChangedBy(userId) //
				.build();
		final ItemEntity finalizedItemEntity = ItemEntityMapper.toEntity(finalizedItem);

		final ItemEntity modifiedItemEntity = this.itemRepository.save(finalizedItemEntity);
		final Item modifiedItem = ItemEntityMapper.toDomain(modifiedItemEntity);

		final ItemHistoryState itemHistoryState = this.buildItemHistoryState(modifiedItem, ItemAction.MODIFY);
		final ItemHistoryStateEntity itemHistoryStateEntity = ItemEntityMapper.toEntity(itemHistoryState);
		this.itemHistoryStateRepository.save(itemHistoryStateEntity);

		return modifiedItem;
	}

	@Transactional
	public void deleteItem(final long itemId, final long userId) {
		final ItemEntity existingItemEntity = this.itemRepository.findById(itemId) //
				.orElseThrow(() -> new ItemNotFoundException("Cannot delete item. Item " + itemId + " not found!"));
		final Item existingItem = ItemEntityMapper.toDomain(existingItemEntity);

		this.itemRepository.delete(existingItemEntity);

		final Item itemDeletionState = ItemBuilder.initFrom(existingItem) //
				.setLastChange(ItemServiceUtils.createNow()) //
				.setLastChangedBy(userId) //
				.build();

		final ItemHistoryState itemHistoryState = this.buildItemHistoryState(itemDeletionState, ItemAction.DELETE);
		final ItemHistoryStateEntity itemHistoryStateEntity = ItemEntityMapper.toEntity(itemHistoryState);
		this.itemHistoryStateRepository.save(itemHistoryStateEntity);
	}

	private ItemHistoryState buildItemHistoryState(final Item item, final ItemAction action) {
		return new ItemHistoryStateBuilder() //
				.setId(null) //
				.setItemId(item.getId()) //
				.setTitle(item.getTitle()) //
				.setMediaType(item.getMediaType()) //
				.setStock(item.getStock()) //
				.setPrice(item.getPrice()) //
				.setImageUrl(item.getImageUrl()) //
				.setLastChange(item.getLastChange()) //
				.setLastChangedBy(item.getLastChangedBy()) //
				.setAction(action) //
				.build();
	}
}
