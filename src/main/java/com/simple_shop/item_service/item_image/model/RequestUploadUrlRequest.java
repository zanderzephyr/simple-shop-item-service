package com.simple_shop.item_service.item_image.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

// TODO Move to common lib (but careful with the item service specifics)
public class RequestUploadUrlRequest {
	private static final Set<String> ALLOWED_FILE_TYPES = RequestUploadUrlRequest.createAllowedFileTypes();

	private static final long MIN_FILE_SIZE_IN_BYTES = 1;

	private static final long MAX_FILE_SIZE_IN_BYTES = 5_000_000;

	private final String fileType;

	private final ImageType imageType;

	private final long fileSizeInBytes;

	private RequestUploadUrlRequest(final RequestUploadUrlRequestBuilder builder) {
		super();

		Objects.requireNonNull(builder);

		builder.validate();

		this.fileType = builder.getFileType();
		this.imageType = builder.getImageType();
		this.fileSizeInBytes = builder.getFileSizeInBytes();
	}

	private static Set<String> createAllowedFileTypes() {
		final Set<String> allowedFileTypes = new HashSet<>();

		allowedFileTypes.add("jpg");
		allowedFileTypes.add("webp");

		return allowedFileTypes;
	}

	public String getFileType() {
		return this.fileType;
	}

	public ImageType getImageType() {
		return this.imageType;
	}

	public long getFileSizeInBytes() {
		return this.fileSizeInBytes;
	}

	public static final class RequestUploadUrlRequestBuilder {
		private String fileType;

		private ImageType imageType;

		private Long fileSizeInBytes;

		public RequestUploadUrlRequestBuilder() {
			super();
		}

		public String getFileType() {
			return this.fileType;
		}

		public RequestUploadUrlRequestBuilder setFileType(final String fileType) {
			this.fileType = fileType;

			return this;
		}

		public ImageType getImageType() {
			return this.imageType;
		}

		public RequestUploadUrlRequestBuilder setImageType(final ImageType imageType) {
			this.imageType = imageType;

			return this;
		}

		public Long getFileSizeInBytes() {
			return this.fileSizeInBytes;
		}

		public RequestUploadUrlRequestBuilder setFileSizeInBytes(final Long fileSizeInBytes) {
			this.fileSizeInBytes = fileSizeInBytes;

			return this;
		}

		public void validate() throws IllegalStateException {
			if (this.fileType == null || this.fileType.isBlank()
					|| !RequestUploadUrlRequest.ALLOWED_FILE_TYPES.contains(this.fileType)) {
				throw new IllegalStateException("Invalid file type!");
			}

			if (this.imageType == null) {
				throw new IllegalStateException("Invalid type!");
			}

			if (this.fileSizeInBytes == null || this.fileSizeInBytes < RequestUploadUrlRequest.MIN_FILE_SIZE_IN_BYTES
					|| this.fileSizeInBytes > RequestUploadUrlRequest.MAX_FILE_SIZE_IN_BYTES) {
				throw new IllegalStateException("Invalid file size in bytes!");
			}
		}

		public RequestUploadUrlRequest build() {
			return new RequestUploadUrlRequest(this);
		}
	}
}