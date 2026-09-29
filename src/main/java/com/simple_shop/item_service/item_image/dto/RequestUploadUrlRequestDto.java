package com.simple_shop.item_service.item_image.dto;

// TODO Move to common lib (but careful with the item service specifics)
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