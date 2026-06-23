package com.simple_shop.item_service.item.persistence;

import java.math.BigDecimal;
import java.time.Instant;

import com.simple_shop.item_service.item.model.MediaType;

// TODO Later: Add Hibernate stuff
public class ItemEntity {
	private Integer id;

	private String title;

	private MediaType mediaType;

	private Integer stock;

	private BigDecimal price;

	private String imageUrl;

	private Instant lastChange;

	private Integer lastChangedBy;

	public ItemEntity() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public ItemEntity setId(final Integer id) {
		this.id = id;

		return this;
	}

	public String getTitle() {
		return this.title;
	}

	public ItemEntity setTitle(final String title) {
		this.title = title;

		return this;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public ItemEntity setMediaType(final MediaType mediaType) {
		this.mediaType = mediaType;

		return this;
	}

	public Integer getStock() {
		return this.stock;
	}

	public ItemEntity setStock(final Integer stock) {
		this.stock = stock;

		return this;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public ItemEntity setPrice(final BigDecimal price) {
		this.price = price;

		return this;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public ItemEntity setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;

		return this;
	}

	public Instant getLastChange() {
		return this.lastChange;
	}

	public ItemEntity setLastChange(final Instant lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Integer getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemEntity setLastChangedBy(final Integer lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}
}