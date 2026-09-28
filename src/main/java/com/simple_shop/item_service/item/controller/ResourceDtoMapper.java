package com.simple_shop.item_service.item.controller;

import com.simple_shop.item_service.item.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item.model.RequestUploadUrlResponse;

// TODO Move to common lib
final class ResourceDtoMapper {

	private ResourceDtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static RequestUploadUrlResponseDto toDto(final RequestUploadUrlResponse requestUploadUrlResponse) {
		final RequestUploadUrlResponseDto requestUploadUrlResponseDtoDto = new RequestUploadUrlResponseDto();

		requestUploadUrlResponseDtoDto.setStorageKey(requestUploadUrlResponse.getStorageKey());
		requestUploadUrlResponseDtoDto.setUploadUrl(requestUploadUrlResponse.getUploadUrl());

		return requestUploadUrlResponseDtoDto;
	}
}
