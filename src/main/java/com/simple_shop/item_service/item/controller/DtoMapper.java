package com.simple_shop.item_service.item.controller;

import java.util.HashMap;
import java.util.Map;

import com.simple_shop.item_service.item.dto.ItemDto;
import com.simple_shop.item_service.item.dto.ItemDto.ItemDtoMember;
import com.simple_shop.item_service.item.model.Item;
import com.simple_shop.item_service.item.model.Item.ItemBuilder;
import com.simple_shop.item_service.item.model.MediaType;
import com.simple_shop.item_service.item.persistence.ItemEntity.ItemEntityMember;
import com.simple_shop.item_service.shared.dto.RequestConstraintsDto;
import com.simple_shop.item_service.shared.exceptions.InvalidMessageException;
import com.simple_shop.item_service.shared.model.RequestConstraints;
import com.simple_shop.item_service.shared.model.SortingDirection;
import com.simple_shop.item_service.shared.model.RequestConstraints.RequestConstraintsBuilder;
import com.simple_shop.item_service.shared.utils.DataTypeUtils;

final class DtoMapper {
	private static final Map<String, String> SORTABLE_DTO_TO_ENTITY_COLUMNS = DtoMapper.collectSortableColumns();

	private DtoMapper() {
		throw new UnsupportedOperationException();
	}

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

	public static Item toDomain(final ItemDto itemDto) {
		try {
			return new ItemBuilder()//
					.setId(itemDto.getId())//
					.setDescription(itemDto.getDescription())//
					.setMediaType(DataTypeUtils.parseEnum(itemDto.getMediaType(), MediaType.class))//
					.setPrice(itemDto.getPrice())//
					.setStock(itemDto.getStock())//
					.setTitle(itemDto.getTitle())//
					.setLastChange(itemDto.getLastChange())//
					.setLastChangedBy(itemDto.getLastChangedBy())//
					.setVersion(itemDto.getVersion())//
					.build();
		} catch (final Exception e) {
			throw new InvalidMessageException(e);
		}
	}

	/*
	 * This is the location to set the precision of decimals, should we ever have
	 * them and need precision. Do not solve this using the formatters.
	 */
	public static ItemDto toDto(final Item item) {
		final ItemDto itemDto = new ItemDto();

		itemDto.setId(item.getId());
		itemDto.setDescription(item.getDescription());
		itemDto.setMediaType(item.getMediaType().name());
		itemDto.setPrice(item.getPrice());
		itemDto.setStock(item.getStock());
		itemDto.setTitle(item.getTitle());
		itemDto.setLastChange(item.getLastChange());
		itemDto.setLastChangedBy(item.getLastChangedBy());
		itemDto.setVersion(item.getVersion());

		return itemDto;
	}

	public static RequestConstraints toDomain(final RequestConstraintsDto requestConstraintsDto) {
		try {
			return new RequestConstraintsBuilder()//
					.setPageIndex(requestConstraintsDto.getPageIndex() != null ? //
							requestConstraintsDto.getPageIndex() : 0)//
					.setPageSize(requestConstraintsDto.getPageSize() != null ? //
							requestConstraintsDto.getPageSize() : 20)//
					.setSortingColumn(DtoMapper.getSortableColumnValue(requestConstraintsDto.getSortingColumn()))//
					.setSortingDirection(requestConstraintsDto.getSortingDirection() != null ? //
							DataTypeUtils.parseEnum(requestConstraintsDto.getSortingDirection(), SortingDirection.class)
							: SortingDirection.ASCENDING)//
					.build();
		} catch (final Exception e) {
			throw new InvalidMessageException(e);
		}
	}

	private static String getSortableColumnValue(final String sortableColumnRequest) {
		String sortableColumnValue;

		if (sortableColumnRequest == null) {
			sortableColumnValue = ItemDtoMember.TITLE.getDomainName();
		} else {
			sortableColumnValue = DtoMapper.SORTABLE_DTO_TO_ENTITY_COLUMNS.get(sortableColumnRequest);
			if (sortableColumnValue == null) {
				sortableColumnValue = ItemDtoMember.TITLE.getDomainName();
			}
		}

		return sortableColumnValue;
	}
}
