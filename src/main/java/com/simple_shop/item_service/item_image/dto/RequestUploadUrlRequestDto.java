package com.simple_shop.item_service.item_image.dto;

public class RequestUploadUrlRequestDto {
	private String imageFileType;

	private String imageType;

	public RequestUploadUrlRequestDto() {
		super();
	}

	public String getImageFileType() {
		return this.imageFileType;
	}

	public RequestUploadUrlRequestDto setImageFileType(final String imageFileType) {
		this.imageFileType = imageFileType;

		return this;
	}

	public String getImageType() {
		return this.imageType;
	}

	public RequestUploadUrlRequestDto setImageType(final String imageType) {
		this.imageType = imageType;

		return this;
	}
}