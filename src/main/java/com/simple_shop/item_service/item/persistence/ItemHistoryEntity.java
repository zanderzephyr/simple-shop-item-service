package com.simple_shop.item_service.item.persistence;

import java.time.LocalDateTime;

import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.model.MediaType;

public class ItemHistoryEntity {
	private Long historyEntryId;

	private Long id;

	private String title;

	private MediaType mediaType;

	private Integer stock;

	private Integer price;

	private String imageUrl;

	private LocalDateTime lastChange;

	private Long lastChangedBy;

	private ItemAction action;

	public ItemHistoryEntity() {
		super();
	}

	public Long getHistoryEntryId() {
		return this.historyEntryId;
	}

	public ItemHistoryEntity setHistoryEntryId(final Long historyEntryId) {
		this.historyEntryId = historyEntryId;

		return this;
	}

	public Long getId() {
		return this.id;
	}

	public ItemHistoryEntity setId(final Long id) {
		this.id = id;

		return this;
	}

	public String getTitle() {
		return this.title;
	}

	public ItemHistoryEntity setTitle(final String title) {
		this.title = title;

		return this;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public ItemHistoryEntity setMediaType(final MediaType mediaType) {
		this.mediaType = mediaType;

		return this;
	}

	public Integer getStock() {
		return this.stock;
	}

	public ItemHistoryEntity setStock(final Integer stock) {
		this.stock = stock;

		return this;
	}

	public Integer getPrice() {
		return this.price;
	}

	public ItemHistoryEntity setPrice(final Integer price) {
		this.price = price;

		return this;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public ItemHistoryEntity setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;

		return this;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public ItemHistoryEntity setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemHistoryEntity setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}

	public ItemAction getAction() {
		return this.action;
	}

	public ItemHistoryEntity setAction(final ItemAction action) {
		this.action = action;

		return this;
	}
}