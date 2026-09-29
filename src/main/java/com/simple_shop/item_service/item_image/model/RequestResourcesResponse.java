package com.simple_shop.item_service.item_image.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// TODO Move to common lib
public class RequestResourcesResponse {
	private final List<String> storageKeys;

	private RequestResourcesResponse(final RequestResourcesResponseBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.storageKeys = new ArrayList<>(builder.getStorageKeys());
	}

	public List<String> getStorageKeys() {
		return Collections.unmodifiableList(this.storageKeys);
	}

	public static final class RequestResourcesResponseBuilder {
		private List<String> storageKeys = new ArrayList<>();

		public RequestResourcesResponseBuilder() {
			super();
		}

		public List<String> getStorageKeys() {
			return this.storageKeys;
		}

		public RequestResourcesResponseBuilder setStorageKeys(final List<String> storageKeys) {
			this.storageKeys = storageKeys == null ? new ArrayList<>() : new ArrayList<>(storageKeys);

			return this;
		}

		public void validate() throws IllegalStateException {
			// Nothing to do so far
		}

		public RequestResourcesResponse build() {
			return new RequestResourcesResponse(this);
		}
	}
}