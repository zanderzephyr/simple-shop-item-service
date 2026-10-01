package com.simple_shop.item_service.item_image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib
@JsonInclude(JsonInclude.Include.NON_NULL)
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