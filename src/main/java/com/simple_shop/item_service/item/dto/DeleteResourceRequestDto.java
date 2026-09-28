package com.simple_shop.item_service.item.dto;

// TODO Move to common lib
public class DeleteResourceRequestDto {
	private String storageKey;

	public DeleteResourceRequestDto() {
		super();
	}

	public String getStorageKey() {
		return this.storageKey;
	}

	public DeleteResourceRequestDto setStorageKey(final String storageKey) {
		this.storageKey = storageKey;

		return this;
	}
}