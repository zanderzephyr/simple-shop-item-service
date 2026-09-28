package com.simple_shop.item_service.item.storage;

import java.time.Duration;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
public class S3StorageClient implements StorageClient {
	private final S3Presigner s3Presigner;
	private final String bucketName;

	public S3StorageClient(final S3Presigner s3Presigner,
			@Value("${app.storage.bucket-name}") final String bucketName) {
		super();

		this.s3Presigner = s3Presigner;
		this.bucketName = bucketName;
	}

	@Override
	public String generatePreSignedUploadUrl(final String storageKey) {
		Objects.requireNonNull(storageKey);

		final PutObjectRequest putObjectRequest = PutObjectRequest.builder()//
				.bucket(this.bucketName)//
				.key(storageKey)//
				.build();

		final PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()//
				.signatureDuration(Duration.ofMinutes(5))//
				.putObjectRequest(putObjectRequest)//
				.build();

		return this.s3Presigner.presignPutObject(presignRequest).url().toString();
	}
}