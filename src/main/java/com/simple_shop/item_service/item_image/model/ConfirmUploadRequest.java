package com.simple_shop.item_service.item_image.model;

import java.util.Objects;

// TODO Move to common lib
public class ConfirmUploadRequest {
	private final String storageKey;

	private ConfirmUploadRequest(final ConfirmUploadRequestBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.storageKey = builder.getStorageKey();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public static final class ConfirmUploadRequestBuilder {
		private String storageKey;

		public ConfirmUploadRequestBuilder() {
			super();
		}

		public String getStorageKey() {
			return this.storageKey;
		}

		public ConfirmUploadRequestBuilder setStorageKey(final String storageKey) {
			this.storageKey = storageKey;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.storageKey == null) {
				throw new IllegalStateException("Invalid storage key!");
			}
		}

		public ConfirmUploadRequest build() {
			return new ConfirmUploadRequest(this);
		}
	}
}