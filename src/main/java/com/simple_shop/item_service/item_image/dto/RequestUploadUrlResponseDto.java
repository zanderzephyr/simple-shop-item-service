package com.simple_shop.item_service.item_image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUploadUrlResponseDto {
	private String uploadUrl;

	private String storageKey;

	public RequestUploadUrlResponseDto() {
		super();
	}

	public String getUploadUrl() {
		return this.uploadUrl;
	}

	public RequestUploadUrlResponseDto setUploadUrl(final String uploadUrl) {
		this.uploadUrl = uploadUrl;

		return this;
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public RequestUploadUrlResponseDto setStorageKey(final String storageKey) {
		this.storageKey = storageKey;

		return this;
	}
}