package com.simple_shop.item_service.item.persistence;

import java.math.BigDecimal;
import java.time.Instant;

import com.simple_shop.item_service.item.model.ItemAction;
import com.simple_shop.item_service.item.model.MediaType;

public class ItemHistoryEntity {
	private Integer historyEntryId;

	private Integer id;

	private String title;

	private MediaType mediaType;

	private Integer stock;

	private BigDecimal price;

	private String imageUrl;

	private Instant lastChange;

	private Integer lastChangedBy;

	private ItemAction action;

	public ItemHistoryEntity() {
		super();
	}

	public Integer getHistoryEntryId() {
		return this.historyEntryId;
	}

	public ItemHistoryEntity setHistoryEntryId(final Integer historyEntryId) {
		this.historyEntryId = historyEntryId;

		return this;
	}

	public Integer getId() {
		return this.id;
	}

	public ItemHistoryEntity setId(final Integer id) {
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

	public BigDecimal getPrice() {
		return this.price;
	}

	public ItemHistoryEntity setPrice(final BigDecimal price) {
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

	public Instant getLastChange() {
		return this.lastChange;
	}

	public ItemHistoryEntity setLastChange(final Instant lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Integer getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemHistoryEntity setLastChangedBy(final Integer lastChangedBy) {
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