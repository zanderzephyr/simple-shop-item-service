package com.simple_shop.item_service.item.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public final class Item {
	private final Integer id;

	private final String title;

	private final MediaType mediaType;

	private final int stock;

	private final BigDecimal price;

	private final String imageUrl;

	private final ZonedDateTime lastChange;

	private final Integer lastChangedBy;

	/**
	 * @param  builder
	 *
	 * @throws ItemValidationException When the builder doesn't validate properly.
	 */
	private Item(final ItemBuilder builder) throws ItemValidationException {
		super();

		builder.validate();

		this.id = builder.getId();
		this.title = builder.getTitle();
		this.mediaType = builder.getMediaType();
		this.stock = builder.getStock();
		this.price = builder.getPrice();
		this.imageUrl = builder.getImageUrl();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
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

	public static final class ItemBuilder {
		private Integer id;

		private String title;

		private MediaType mediaType;

		private Integer stock;

		private BigDecimal price;

		private String imageUrl;

		private ZonedDateTime lastChange;

		private Integer lastChangedBy;

		public ItemBuilder() {
			super();
		}

		public Integer getId() {
			return this.id;
		}

		public ItemBuilder setId(final Integer id) {
			this.id = id;

			return this;
		}

		public String getTitle() {
			return this.title;
		}

		public ItemBuilder setTitle(final String title) {
			this.title = title;

			return this;
		}

		public MediaType getMediaType() {
			return this.mediaType;
		}

		public ItemBuilder setMediaType(final MediaType mediaType) {
			this.mediaType = mediaType;

			return this;
		}

		public Integer getStock() {
			return this.stock;
		}

		public ItemBuilder setStock(final Integer stock) {
			this.stock = stock;

			return this;
		}

		public BigDecimal getPrice() {
			return this.price;
		}

		public ItemBuilder setPrice(final BigDecimal price) {
			this.price = price;

			return this;
		}

		public String getImageUrl() {
			return this.imageUrl;
		}

		public ItemBuilder setImageUrl(final String imageUrl) {
			this.imageUrl = imageUrl;

			return this;
		}

		public ZonedDateTime getLastChange() {
			return this.lastChange;
		}

		public ItemBuilder setLastChange(final ZonedDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Integer getLastChangedBy() {
			return this.lastChangedBy;
		}

		public ItemBuilder setLastChangedBy(final Integer lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		/**
		 * @throws ItemValidationException When a field is not properly set.
		 */
		public void validate() throws ItemValidationException {
			if (this.title == null || this.title.isBlank()) {
				throw new ItemValidationException("Invalid title!");
			}

			if (this.mediaType == null) {
				throw new ItemValidationException("Invalid media type!");
			}

			if (this.stock == null || this.stock < 0) {
				throw new ItemValidationException("Invalid stock!");
			}

			if (this.price == null || this.price.compareTo(BigDecimal.ZERO) < 0) {
				throw new ItemValidationException("Invalid price!");
			}

			if (this.imageUrl == null || this.imageUrl.isBlank()) {
				throw new ItemValidationException("Invalid Image URL!");
			}
		}

		/**
		 * @return
		 *
		 * @throws ItemValidationException When the builder doesn't validate properly.
		 */
		public Item build() throws ItemValidationException {
			return new Item(this);
		}

		public static ItemBuilder initFrom(final Item item) {
			return new ItemBuilder()//
					.setId(item.getId())//
					.setImageUrl(item.getImageUrl())//
					.setLastChange(item.getLastChange())//
					.setLastChangedBy(item.getLastChangedBy())//
					.setMediaType(item.getMediaType())//
					.setPrice(item.getPrice())//
					.setStock(item.getStock())//
					.setTitle(item.getTitle());
		}
	}
}