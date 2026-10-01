package com.simple_shop.item_service.item_image.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// TODO Move to common lib
public class RequestResourcesResponse {
	private final String thumbnailImageStorageKey;

	private final List<String> galleryImageStorageKeys;

	private RequestResourcesResponse(final RequestResourcesResponseBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.thumbnailImageStorageKey = builder.getThumbnailImageStorageKey();
		this.galleryImageStorageKeys = new ArrayList<>(builder.getGalleryImageStorageKeys());
	}

	public String getThumbnailImageStorageKey() {
		return this.thumbnailImageStorageKey;
	}

	public List<String> getGalleryImageStorageKeys() {
		return Collections.unmodifiableList(this.galleryImageStorageKeys);
	}

	public static final class RequestResourcesResponseBuilder {
		private String thumbnailImageStorageKey;

		private List<String> galleryImageStorageKeys = new ArrayList<>();

		public RequestResourcesResponseBuilder() {
			super();
		}

		public String getThumbnailImageStorageKey() {
			return this.thumbnailImageStorageKey;
		}

		public RequestResourcesResponseBuilder setThumbnailImageStorageKey(final String thumbnailImageStorageKey) {
			this.thumbnailImageStorageKey = thumbnailImageStorageKey;

			return this;
		}

		public List<String> getGalleryImageStorageKeys() {
			return this.galleryImageStorageKeys;
		}

		public RequestResourcesResponseBuilder setGalleryImageStorageKeys(final List<String> galleryImageStorageKeys) {
			this.galleryImageStorageKeys = galleryImageStorageKeys == null ? new ArrayList<>()
					: new ArrayList<>(galleryImageStorageKeys);

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