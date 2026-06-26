package com.simple_shop.item_service.item.persistence;

import java.time.LocalDateTime;

import com.simple_shop.item_service.item.model.MediaType;

// TODO Later: Add Hibernate stuff
public class ItemEntity {
	private Long id;

	private String title;

	private MediaType mediaType;

	private Integer stock;

	private Integer price;

	private String imageUrl;

	/**
	 * In UTC.
	 */
	private LocalDateTime lastChange;

	private Long lastChangedBy;

	public ItemEntity() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(final MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(final Integer stock) {
		this.stock = stock;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setPrice(final Integer price) {
		this.price = price;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(final String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public void setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public void setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public static enum ItemEntityMember {
		ID("id"),

		TITLE("title"),

		MEDIA_TYPE("mediaType"),

		STOCK("stock"),

		PRICE("price"),

		IMAGE_URL("imageUrl"),

		LAST_CHANGE("lastChange"),

		LAST_CHANGED_BY("lastChangedBy"),

		;

		private final String domainName;

		private ItemEntityMember(final String domainName) {
			this.domainName = domainName;
		}

		public String getDomainName() {
			return this.domainName;
		}
	}
}