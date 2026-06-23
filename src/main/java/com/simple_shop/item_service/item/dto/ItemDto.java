package com.simple_shop.item_service.item.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.simple_shop.item_service.item.model.MediaType;

public class ItemDto {
	private Integer id;

	private String title;

	private MediaType mediaType;

	private Integer stock;

	private BigDecimal price;

	private String imageUrl;

	private Instant lastChange;

	private Integer lastChangedBy;

	public ItemDto() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public ItemDto setId(final Integer id) {
		this.id = id;

		return this;
	}

	public String getTitle() {
		return this.title;
	}

	public ItemDto setTitle(final String title) {
		this.title = title;

		return this;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public ItemDto setMediaType(final MediaType mediaType) {
		this.mediaType = mediaType;

		return this;
	}

	public Integer getStock() {
		return this.stock;
	}

	public ItemDto setStock(final Integer stock) {
		this.stock = stock;

		return this;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public ItemDto setPrice(final BigDecimal price) {
		this.price = price;

		return this;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public ItemDto setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;

		return this;
	}

	public Instant getLastChange() {
		return this.lastChange;
	}

	public ItemDto setLastChange(final Instant lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Integer getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemDto setLastChangedBy(final Integer lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}
}