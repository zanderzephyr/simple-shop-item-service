package com.simple_shop.item_service.item.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public final class ItemHistory {
	private final Integer historyEntryId;

	private final Integer id;

	private final String title;

	private final MediaType mediaType;

	private final int stock;

	private final BigDecimal price;

	private final String imageUrl;

	private final ZonedDateTime lastChange;

	private final Integer lastChangedBy;

	private final ItemAction action;

	private ItemHistory(final ItemHistoryBuilder builder) {
		super();

		builder.validate();

		this.historyEntryId = builder.getHistoryEntryId();
		this.id = builder.getId();
		this.title = builder.getTitle();
		this.mediaType = builder.getMediaType();
		this.stock = builder.getStock();
		this.price = builder.getPrice();
		this.imageUrl = builder.getImageUrl();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.action = builder.getAction();
	}

	public Integer getHistoryEntryId() {
		return this.historyEntryId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public MediaType getMediaType() {
		return this.mediaType;
	}

	public int getStock() {
		return this.stock;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public ZonedDateTime getLastChange() {
		return this.lastChange;
	}

	public Integer getLastChangedBy() {
		return this.lastChangedBy;
	}

	public ItemAction getAction() {
		return this.action;
	}

	public static final class ItemHistoryBuilder {
		private Integer historyEntryId;

		private Integer id;

		private String title;

		private MediaType mediaType;

		private Integer stock;

		private BigDecimal price;

		private String imageUrl;

		private ZonedDateTime lastChange;

		private Integer lastChangedBy;

		private ItemAction action;

		public ItemHistoryBuilder() {
			super();
		}

		public Integer getHistoryEntryId() {
			return this.historyEntryId;
		}

		public ItemHistoryBuilder setHistoryEntryId(final Integer historyEntryId) {
			this.historyEntryId = historyEntryId;

			return this;
		}

		public Integer getId() {
			return this.id;
		}

		public ItemHistoryBuilder setId(final Integer id) {
			this.id = id;

			return this;
		}

		public String getTitle() {
			return this.title;
		}

		public ItemHistoryBuilder setTitle(final String title) {
			this.title = title;

			return this;
		}

		public MediaType getMediaType() {
			return this.mediaType;
		}

		public ItemHistoryBuilder setMediaType(final MediaType mediaType) {
			this.mediaType = mediaType;

			return this;
		}

		public Integer getStock() {
			return this.stock;
		}

		public ItemHistoryBuilder setStock(final Integer stock) {
			this.stock = stock;

			return this;
		}

		public BigDecimal getPrice() {
			return this.price;
		}

		public ItemHistoryBuilder setPrice(final BigDecimal price) {
			this.price = price;

			return this;
		}

		public String getImageUrl() {
			return this.imageUrl;
		}

		public ItemHistoryBuilder setImageUrl(final String imageUrl) {
			this.imageUrl = imageUrl;

			return this;
		}

		public ZonedDateTime getLastChange() {
			return this.lastChange;
		}

		public ItemHistoryBuilder setLastChange(final ZonedDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Integer getLastChangedBy() {
			return this.lastChangedBy;
		}

		public ItemHistoryBuilder setLastChangedBy(final Integer lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public ItemAction getAction() {
			return this.action;
		}

		public ItemHistoryBuilder setAction(final ItemAction action) {
			this.action = action;

			return this;
		}

		public void validate() {
			if (this.title == null || this.title.isBlank()) {
				throw new IllegalArgumentException("Invalid title!");
			}

			if (this.mediaType == null) {
				throw new IllegalArgumentException("Invalid media type!");
			}

			if (this.stock == null || this.stock < 0) {
				throw new IllegalArgumentException("Invalid stock!");
			}

			if (this.price == null || this.price.compareTo(BigDecimal.ZERO) < 0) {
				throw new IllegalArgumentException("Invalid price!");
			}

			if (this.imageUrl == null || this.imageUrl.isBlank()) {
				throw new IllegalArgumentException("Invalid Image URL!");
			}

			if (this.action == null) {
				throw new IllegalArgumentException("Invalid action!");
			}
		}

		public ItemHistory build() {
			return new ItemHistory(this);
		}

		public static ItemHistoryBuilder initFrom(final ItemHistory itemHistory) {
			return new ItemHistoryBuilder()//
					.setHistoryEntryId(itemHistory.getHistoryEntryId())//
					.setId(itemHistory.getId())//
					.setImageUrl(itemHistory.getImageUrl())//
					.setLastChange(itemHistory.getLastChange())//
					.setLastChangedBy(itemHistory.getLastChangedBy())//
					.setMediaType(itemHistory.getMediaType())//
					.setPrice(itemHistory.getPrice())//
					.setStock(itemHistory.getStock())//
					.setTitle(itemHistory.getTitle())//
					.setAction(itemHistory.getAction());
		}
	}
}