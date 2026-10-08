package com.simple_shop.item_service.item_image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmUploadResponseDto {
	private String storageKey;

	public ConfirmUploadResponseDto() {
		super();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public ConfirmUploadResponseDto setStorageKey(final String storageKey) {
		this.storageKey = storageKey;

		return this;
	}
}