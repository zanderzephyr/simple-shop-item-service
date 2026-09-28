package com.simple_shop.item_service.item.dto;

import java.util.ArrayList;
import java.util.List;

// TODO Move to common lib
public class RequestResourcesResponseDto {
	private List<String> storageKeys;

	public RequestResourcesResponseDto() {
		super();
	}

	public List<String> getStorageKeys() {
		return this.storageKeys;
	}

	public RequestResourcesResponseDto setStorageKeys(final List<String> storageKeys) {
		if (storageKeys != null) {
			this.storageKeys = new ArrayList<>(storageKeys);
		} else {
			this.storageKeys = null;
		}

		return this;
	}
}