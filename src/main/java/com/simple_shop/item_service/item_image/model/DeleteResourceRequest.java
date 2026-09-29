package com.simple_shop.item_service.item_image.model;

import java.util.Objects;

// TODO Move to common lib
public class DeleteResourceRequest {
	private final String storageKey;

	private DeleteResourceRequest(final DeleteResourceRequestBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.storageKey = builder.getStorageKey();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public static final class DeleteResourceRequestBuilder {
		private String storageKey;

		public DeleteResourceRequestBuilder() {
			super();
		}

		public String getStorageKey() {
			return this.storageKey;
		}

		public DeleteResourceRequestBuilder setStorageKey(final String storageKey) {
			this.storageKey = storageKey;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.storageKey == null) {
				throw new IllegalStateException("Invalid storage key!");
			}
		}

		public DeleteResourceRequest build() {
			return new DeleteResourceRequest(this);
		}
	}
}