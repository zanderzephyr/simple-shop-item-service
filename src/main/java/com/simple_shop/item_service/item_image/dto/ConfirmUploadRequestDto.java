package com.simple_shop.item_service.item_image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfirmUploadRequestDto {
	private String storageKey;

	public ConfirmUploadRequestDto() {
		super();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public ConfirmUploadRequestDto setStorageKey(final String storageKey) {
		this.storageKey = storageKey;

		return this;
	}
}