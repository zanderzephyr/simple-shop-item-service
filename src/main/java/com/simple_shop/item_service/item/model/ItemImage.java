package com.simple_shop.item_service.item.model;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ItemImage {
	private final Long id;

	private final Long itemId;

	private final String storageKey;

	private final ImageType imageType;

	private final ImageState imageState;

	private final LocalDateTime createdAt;

	private ItemImage(final ItemImageBuilder builder) throws IllegalStateException {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.id = builder.getId();
		this.itemId = builder.getItemId();
		this.storageKey = builder.getStorageKey();
		this.imageType = builder.getImageType();
		this.imageState = builder.getImageState();
		this.createdAt = builder.getCreatedAt();
	}

	public Long getId() {
		return this.id;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public ImageType getImageType() {
		return this.imageType;
	}

	public ImageState getImageState() {
		return this.imageState;
	}

	public LocalDateTime getCreatedAt() {
		return this.createdAt;
	}

	public static final class ItemImageBuilder {
		private Long id;

		private Long itemId;

		private String storageKey;

		private ImageType imageType;

		private ImageState imageState;

		private LocalDateTime createdAt;

		public ItemImageBuilder() {
			super();
		}

		public Long getId() {
			return this.id;
		}

		public ItemImageBuilder setId(final Long id) {
			this.id = id;

			return this;
		}

		public Long getItemId() {
			return this.itemId;
		}

		public ItemImageBuilder setItemId(final Long itemId) {
			this.itemId = itemId;

			return this;
		}

		public String getStorageKey() {
			return this.storageKey;
		}

		public ItemImageBuilder setStorageKey(final String storageKey) {
			this.storageKey = storageKey;

			return this;
		}

		public ImageType getImageType() {
			return this.imageType;
		}

		public ItemImageBuilder setImageType(final ImageType imageType) {
			this.imageType = imageType;

			return this;
		}

		public ImageState getImageState() {
			return this.imageState;
		}

		public ItemImageBuilder setImageState(final ImageState imageState) {
			this.imageState = imageState;

			return this;
		}

		public LocalDateTime getCreatedAt() {
			return this.createdAt;
		}

		public ItemImageBuilder setCreatedAt(final LocalDateTime createdAt) {
			this.createdAt = createdAt;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.id != null && this.id < 0) {
				throw new IllegalStateException("Invalid ID!");
			}

			if (this.itemId == null || this.itemId < 0) {
				throw new IllegalStateException("Invalid item ID!");
			}

			if (this.storageKey == null || this.storageKey.isBlank() || this.storageKey.length() > 1024) {
				throw new IllegalStateException("Invalid storage key!");
			}

			if (this.imageType == null) {
				throw new IllegalStateException("Invalid image type!");
			}

			if (this.imageState == null) {
				throw new IllegalStateException("Invalid image state!");
			}

			if (this.createdAt == null) {
				throw new IllegalStateException("Invalid Created At!");
			}
		}

		public ItemImage build() throws IllegalStateException {
			return new ItemImage(this);
		}
	}
}