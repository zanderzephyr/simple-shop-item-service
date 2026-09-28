package com.simple_shop.item_service.item.model;

import java.util.Objects;

// TODO Move to common lib
public class RequestUploadUrlResponse {
	private final String uploadUrl;

	private final String storageKey;

	private RequestUploadUrlResponse(final RequestUploadUrlResponseBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.uploadUrl = builder.getUploadUrl();
		this.storageKey = builder.getStorageKey();
	}

	public String getUploadUrl() {
		return this.uploadUrl;
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public static final class RequestUploadUrlResponseBuilder {
		private String uploadUrl;

		private String storageKey;

		public RequestUploadUrlResponseBuilder() {
			super();
		}

		public String getUploadUrl() {
			return this.uploadUrl;
		}

		public RequestUploadUrlResponseBuilder setUploadUrl(final String uploadUrl) {
			this.uploadUrl = uploadUrl;

			return this;
		}

		public String getStorageKey() {
			return this.storageKey;
		}

		public RequestUploadUrlResponseBuilder setStorageKey(final String storageKey) {
			this.storageKey = storageKey;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.storageKey == null || this.storageKey.isBlank()) {
				throw new IllegalStateException("Invalid storage key!");
			}

			if (this.uploadUrl == null || this.uploadUrl.isBlank()) {
				throw new IllegalStateException("Invalid upload url!");
			}
		}

		public RequestUploadUrlResponse build() {
			return new RequestUploadUrlResponse(this);
		}
	}
}