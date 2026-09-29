package com.simple_shop.item_service.item.controller;

import com.simple_shop.item_service.item.dto.ConfirmUploadRequestDto;
import com.simple_shop.item_service.item.dto.DeleteResourceRequestDto;
import com.simple_shop.item_service.item.dto.RequestUploadUrlRequestDto;
import com.simple_shop.item_service.item.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item.exceptions.InvalidMessageException;
import com.simple_shop.item_service.item.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item.model.ConfirmUploadRequest.ConfirmUploadRequestBuilder;
import com.simple_shop.item_service.item.model.DeleteResourceRequest;
import com.simple_shop.item_service.item.model.DeleteResourceRequest.DeleteResourceRequestBuilder;
import com.simple_shop.item_service.item.model.ImageFileType;
import com.simple_shop.item_service.item.model.ImageType;
import com.simple_shop.item_service.item.model.RequestUploadUrlRequest;
import com.simple_shop.item_service.item.model.RequestUploadUrlRequest.RequestUploadUrlRequestBuilder;
import com.simple_shop.item_service.item.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.utils.DataTypeUtils;

// TODO Move to common lib
final class ResourceDtoMapper {

	private ResourceDtoMapper() {
		throw new UnsupportedOperationException();
	}

	public static RequestUploadUrlRequest toDomain(final RequestUploadUrlRequestDto requestUploadUrlRequest) {
		try {
			return new RequestUploadUrlRequestBuilder()//
					.setImageFileType(
							DataTypeUtils.parseEnum(requestUploadUrlRequest.getImageFileType(), ImageFileType.class))//
					.setImageType(DataTypeUtils.parseEnum(requestUploadUrlRequest.getImageType(), ImageType.class))//
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
}
