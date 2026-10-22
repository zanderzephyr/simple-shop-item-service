package com.simple_shop.item_service.item.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.simple_shop.item_service.shared.dto.formatter.JsonLocalDateTimeFormatter;
import com.simple_shop.item_service.shared.dto.formatter.JsonLongFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {
	@JsonLongFormatter
	private Long id;

	private String title;

	private String mediaType;

	private Integer stock;

	private Integer price;

	private String description;

	/**
	 * In UTC.
	 */
	@JsonLocalDateTimeFormatter
	private LocalDateTime lastChange;

	@JsonLongFormatter
	private Long lastChangedBy;

	@JsonLongFormatter
	private Long version;

	public ItemDto() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public ItemDto setId(final Long id) {
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

	public String getMediaType() {
		return this.mediaType;
	}

	public ItemDto setMediaType(final String mediaType) {
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

	public Integer getPrice() {
		return this.price;
	}

	public ItemDto setPrice(final Integer price) {
		this.price = price;

		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public ItemDto setDescription(final String description) {
		this.description = description;

		return this;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public ItemDto setLastChange(final LocalDateTime lastChange) {
		this.lastChange = lastChange;

		return this;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemDto setLastChangedBy(final Long lastChangedBy) {
		this.lastChangedBy = lastChangedBy;

		return this;
	}

	public Long getVersion() {
		return this.version;
	}

	public ItemDto setVersion(final Long version) {
		this.version = version;

		return this;
	}

	@Override
	public boolean equals(final Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof final ItemDto other)) {
			return false;
		}

		return Objects.equals(this.description, other.description) && //
				Objects.equals(this.id, other.id) && //
				Objects.equals(this.lastChange, other.lastChange) && //
				Objects.equals(this.lastChangedBy, other.lastChangedBy) && //
				Objects.equals(this.mediaType, other.mediaType) && //
				Objects.equals(this.price, other.price) && //
				Objects.equals(this.stock, other.stock) && //
				Objects.equals(this.title, other.title) && //
				Objects.equals(this.version, other.version);
	}

	@Override
	public int hashCode() {
		return Objects.hash(//
				this.description, //
				this.id, //
				this.lastChange, //
				this.lastChangedBy, //
				this.mediaType, //
				this.price, //
				this.stock, //
				this.title, //
				this.version);
	}

	public static enum ItemDtoMember {
		ID("id"),

		TITLE("title"),

		MEDIA_TYPE("mediaType"),

		STOCK("stock"),

		PRICE("price"),

		DESCRIPTION("description"),

		LAST_CHANGE("lastChange"),

		LAST_CHANGED_BY("lastChangedBy"),

		VERSION("version"),

		;

		private final String domainName;

		private ItemDtoMember(final String domainName) {
			this.domainName = domainName;
		}

		public String getDomainName() {
			return this.domainName;
		}
	}
}