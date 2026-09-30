package com.simple_shop.item_service.item_image.controller;

import com.simple_shop.item_service.item_image.dto.ConfirmUploadRequestDto;
import com.simple_shop.item_service.item_image.dto.DeleteResourceRequestDto;
import com.simple_shop.item_service.item_image.dto.RequestResourcesResponseDto;
import com.simple_shop.item_service.item_image.dto.RequestUploadUrlRequestDto;
import com.simple_shop.item_service.item_image.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest.ConfirmUploadRequestBuilder;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest.DeleteResourceRequestBuilder;
import com.simple_shop.item_service.item_image.model.ImageType;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlRequest;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlRequest.RequestUploadUrlRequestBuilder;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.shared.exceptions.InvalidMessageException;
import com.simple_shop.item_service.shared.utils.DataTypeUtils;

// TODO Move to common lib (but careful with the item service specifics)
final class ResourceDtoMapper {

	private ResourceDtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static RequestUploadUrlRequest toDomain(final RequestUploadUrlRequestDto requestUploadUrlRequest) {
		try {
			return new RequestUploadUrlRequestBuilder()//
					.setFileType(requestUploadUrlRequest.getFileType())//
					.setImageType(DataTypeUtils.parseEnum(requestUploadUrlRequest.getImageType(), ImageType.class))//
					.setFileSizeInBytes(requestUploadUrlRequest.getFileSizeInBytes())//
					.build();
		} catch (final Exception e) {
			throw new InvalidMessageException(e);
		}
	}

	public static RequestUploadUrlResponseDto toDto(final RequestUploadUrlResponse requestUploadUrlResponse) {
		final RequestUploadUrlResponseDto requestUploadUrlResponseDto = new RequestUploadUrlResponseDto();

		requestUploadUrlResponseDto.setStorageKey(requestUploadUrlResponse.getStorageKey());
		requestUploadUrlResponseDto.setUploadUrl(requestUploadUrlResponse.getUploadUrl());

		return requestUploadUrlResponseDto;
	}

	public static ConfirmUploadRequest toDomain(final ConfirmUploadRequestDto confirmUploadRequest) {
		try {
			return new ConfirmUploadRequestBuilder()//
					.setStorageKey(confirmUploadRequest.getStorageKey())//
					.build();
		} catch (final Exception e) {
			throw new InvalidMessageException(e);
		}
	}

	public static DeleteResourceRequest toDomain(final DeleteResourceRequestDto deleteResourceRequest) {
		try {
			return new DeleteResourceRequestBuilder()//
					.setStorageKey(deleteResourceRequest.getStorageKey())//
					.build();
		} catch (final Exception e) {
			throw new InvalidMessageException(e);
		}
	}

	public static RequestResourcesResponseDto toDto(final RequestResourcesResponse requestResourcesResponse) {
		final RequestResourcesResponseDto requestResourcesResponseDto = new RequestResourcesResponseDto();

		requestResourcesResponseDto.setStorageKeys(requestResourcesResponse.getStorageKeys());

		return requestResourcesResponseDto;
	}
}
