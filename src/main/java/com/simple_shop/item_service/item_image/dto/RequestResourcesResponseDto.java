package com.simple_shop.item_service.item_image.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestResourcesResponseDto {
	private String thumbnailImageStorageKey;

	private List<String> galleryImageStorageKeys;

	public RequestResourcesResponseDto() {
		super();
	}

	public String getThumbnailImageStorageKey() {
		return this.thumbnailImageStorageKey;
	}

	public RequestResourcesResponseDto setThumbnailImageStorageKey(final String thumbnailImageStorageKey) {
		this.thumbnailImageStorageKey = thumbnailImageStorageKey;

		return this;
	}

	public List<String> getGalleryImageStorageKeys() {
		return this.galleryImageStorageKeys;
	}

	public RequestResourcesResponseDto setGalleryImageStorageKeys(final List<String> galleryImageStorageKeys) {
		if (galleryImageStorageKeys != null && !galleryImageStorageKeys.isEmpty()) {
			this.galleryImageStorageKeys = new ArrayList<>(galleryImageStorageKeys);
		} else {
			this.galleryImageStorageKeys = null;
		}

		return this;
	}
}