package com.simple_shop.item_service.item_image.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

// TODO Move to common lib (but careful with the item service specifics)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUploadUrlRequestDto {
	private String fileType;

	private String imageType;

	private Long fileSizeInBytes;

	public RequestUploadUrlRequestDto() {
		super();
	}

	public String getFileType() {
		return this.fileType;
	}

	public RequestUploadUrlRequestDto setFileType(final String fileType) {
		this.fileType = fileType;

		return this;
	}

	public Long getFileSizeInBytes() {
		return this.fileSizeInBytes;
	}

	public RequestUploadUrlRequestDto setFileSizeInBytes(final Long fileSizeInBytes) {
		this.fileSizeInBytes = fileSizeInBytes;

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