package com.simple_shop.item_service.item.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ItemHistoryState {
	private final Long id;

	private final Long itemId;

	private final String title;

	private final MediaType mediaType;

	private final int stock;

	private final int price;

	private final String description;

	private final ItemAction action;

	private final LocalDateTime lastChange;

	private final Long lastChangedBy;

	private final Long version;

	private ItemHistoryState(final ItemHistoryStateBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.id = builder.getId();
		this.itemId = builder.getItemId();
		this.title = builder.getTitle();
		this.mediaType = builder.getMediaType();
		this.stock = builder.getStock();
		this.price = builder.getPrice();
		this.description = builder.getDescription();
		this.action = builder.getAction();
		this.lastChange = builder.getLastChange();
		this.lastChangedBy = builder.getLastChangedBy();
		this.version = builder.getVersion();
	}

	public Long getId() {
		return this.id;
	}

	public Long getItemId() {
		return this.itemId;
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

	public int getPrice() {
		return this.price;
	}

	public String getDescription() {
		return this.description;
	}

	public ItemAction getAction() {
		return this.action;
	}

	public LocalDateTime getLastChange() {
		return this.lastChange;
	}

	public Long getLastChangedBy() {
		return this.lastChangedBy;
	}

	public Long getVersion() {
		return this.version;
	}

	public static final class ItemHistoryStateBuilder {
		private Long id;

		private Long itemId;

		private String title;

		private MediaType mediaType;

		private Integer stock;

		private Integer price;

		private String description;

		private ItemAction action;

		private LocalDateTime lastChange;

		private Long lastChangedBy;

		private Long version;

		public ItemHistoryStateBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public ItemHistoryStateBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public Long getItemId() {
			return this.itemId;
		}

		public ItemHistoryStateBuilder setItemId(final Long itemId) {
			this.itemId = itemId;

			return this;
		}

		public String getTitle() {
			return this.title;
		}

		public ItemHistoryStateBuilder setTitle(final String title) {
			this.title = title;

			return this;
		}

		public MediaType getMediaType() {
			return this.mediaType;
		}

		public ItemHistoryStateBuilder setMediaType(final MediaType mediaType) {
			this.mediaType = mediaType;

			return this;
		}

		public Integer getStock() {
			return this.stock;
		}

		public ItemHistoryStateBuilder setStock(final Integer stock) {
			this.stock = stock;

			return this;
		}

		public Integer getPrice() {
			return this.price;
		}

		public ItemHistoryStateBuilder setPrice(final Integer price) {
			this.price = price;

			return this;
		}

		public String getDescription() {
			return this.description;
		}

		public ItemHistoryStateBuilder setDescription(final String description) {
			this.description = description;

			return this;
		}

		public ItemAction getAction() {
			return this.action;
		}

		public ItemHistoryStateBuilder setAction(final ItemAction action) {
			this.action = action;

			return this;
		}

		public LocalDateTime getLastChange() {
			return this.lastChange;
		}

		public ItemHistoryStateBuilder setLastChange(final LocalDateTime lastChange) {
			this.lastChange = lastChange;

			return this;
		}

		public Long getLastChangedBy() {
			return this.lastChangedBy;
		}

		public ItemHistoryStateBuilder setLastChangedBy(final Long lastChangedBy) {
			this.lastChangedBy = lastChangedBy;

			return this;
		}

		public Long getVersion() {
			return this.version;
		}

		public ItemHistoryStateBuilder setVersion(final Long version) {
			this.version = version;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.id != null && this.id < 0) {
				throw new IllegalStateException("Invalid ID!");
			}

			if (this.title == null || this.title.isBlank() || this.title.length() > 400) {
				throw new IllegalStateException("Invalid title!");
			}

			if (this.mediaType == null) {
				throw new IllegalStateException("Invalid media type!");
			}

			if (this.stock == null || this.stock < 0) {
				throw new IllegalStateException("Invalid stock!");
			}

			if (this.price == null || this.price < 0) {
				throw new IllegalStateException("Invalid price!");
			}

			if (this.description == null || this.description.isBlank() || this.description.length() > 10000) {
				throw new IllegalStateException("Invalid description!");
			}

			if (this.action == null) {
				throw new IllegalStateException("Invalid action!");
			}

			if (this.lastChange == null) {
				throw new IllegalStateException("Invalid last change!");
			}

			if (this.lastChangedBy != null && this.lastChangedBy < 0) {
				throw new IllegalStateException("Invalid last changed by!");
			}

			if (this.version != null && this.version < 0) {
				throw new IllegalStateException("Invalid version!");
			}
		}

		public ItemHistoryState build() throws IllegalStateException {
			return new ItemHistoryState(this);
		}

		public static ItemHistoryStateBuilder initFrom(final ItemHistoryState itemHistory) {
			return new ItemHistoryStateBuilder()//
					.setId(itemHistory.getId())//
					.setItemId(itemHistory.getItemId())//
					.setDescription(itemHistory.getDescription())//
					.setMediaType(itemHistory.getMediaType())//
					.setPrice(itemHistory.getPrice())//
					.setStock(itemHistory.getStock())//
					.setTitle(itemHistory.getTitle())//
					.setAction(itemHistory.getAction())//
					.setLastChange(itemHistory.getLastChange())//
					.setLastChangedBy(itemHistory.getLastChangedBy())//
					.setVersion(itemHistory.getVersion());
		}
	}
}