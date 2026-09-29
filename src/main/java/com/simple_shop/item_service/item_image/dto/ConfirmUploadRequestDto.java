package com.simple_shop.item_service.item_image.dto;

// TODO Move to common lib
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