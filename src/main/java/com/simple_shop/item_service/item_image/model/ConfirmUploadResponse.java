package com.simple_shop.item_service.item_image.model;

import java.util.Objects;

// TODO Move to common lib
public class ConfirmUploadResponse {
	private final String storageKey;

	private ConfirmUploadResponse(final ConfirmUploadResponseBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.storageKey = builder.getStorageKey();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public static final class ConfirmUploadResponseBuilder {
		private String storageKey;

		public ConfirmUploadResponseBuilder() {
			super();
		}

		public String getStorageKey() {
			return this.storageKey;
		}

		public ConfirmUploadResponseBuilder setStorageKey(final String storageKey) {
			this.storageKey = storageKey;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.storageKey == null) {
				throw new IllegalStateException("Invalid storage key!");
			}
		}

		public ConfirmUploadResponse build() {
			return new ConfirmUploadResponse(this);
		}
	}
}