package com.simple_shop.item_service.item_image.model;

import java.util.Objects;

// TODO Move to common lib
public class RequestUploadUrlRequest {
	private final ImageFileType imageFileType;

	private final ImageType imageType;

	private RequestUploadUrlRequest(final RequestUploadUrlRequestBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.imageFileType = builder.getImageFileType();
		this.imageType = builder.getImageType();
	}

	public ImageFileType getImageFileType() {
		return this.imageFileType;
	}

	public ImageType getImageType() {
		return this.imageType;
	}

	public static final class RequestUploadUrlRequestBuilder {
		private ImageFileType imageFileType;

		private ImageType imageType;

		public RequestUploadUrlRequestBuilder() {
			super();
		}

		public ImageFileType getImageFileType() {
			return this.imageFileType;
		}

		public RequestUploadUrlRequestBuilder setImageFileType(final ImageFileType uploadUrl) {
			this.imageFileType = uploadUrl;

			return this;
		}

		public ImageType getImageType() {
			return this.imageType;
		}

		public RequestUploadUrlRequestBuilder setImageType(final ImageType imageType) {
			this.imageType = imageType;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.imageFileType == null) {
				throw new IllegalStateException("Invalid image file type!");
			}

			if (this.imageType == null) {
				throw new IllegalStateException("Invalid type!");
			}
		}

		public RequestUploadUrlRequest build() {
			return new RequestUploadUrlRequest(this);
		}
	}
}