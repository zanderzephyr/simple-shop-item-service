package com.simple_shop.item_service.item.storage;

import java.time.Duration;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
public class S3StorageClient implements StorageClient {
	private final S3Client s3Client;
	private final S3Presigner s3Presigner;
	private final String bucketName;

	public S3StorageClient(//
			final S3Presigner s3Presigner, //
			final S3Client s3Client, //
			@Value("${app.storage.bucket-name}") final String bucketName) {
		super();

		this.s3Client = s3Client;
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

	@Override
	public void deleteObject(final String storageKey) {
		Objects.requireNonNull(storageKey);

		final DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()//
				.bucket(this.bucketName)//
				.key(storageKey)//
				.build();

		this.s3Client.deleteObject(deleteObjectRequest);
	}

	/*
	 * We use the throwable path here as this is highly efficient. The alternative,
	 * i.e. requesting the content of the key with a list request, looks a bit
	 * cleaner but costs more in terms of CPU and traffic in AWS according to Gemini
	 * on 29.09.2026.
	 */
	@Override
	public boolean doesObjectExist(final String storageKey) {
		Objects.requireNonNull(storageKey);

		try {
			final HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()//
					.bucket(this.bucketName)//
					.key(storageKey)//
					.build();
			this.s3Client.headObject(headObjectRequest);
			return true;
		} catch (final NoSuchKeyException e) {
			return false;
		}
	}

	@Override
	public void copyObject(final String sourceKey, final String destinationKey) {
		Objects.requireNonNull(sourceKey);
		Objects.requireNonNull(destinationKey);

		final CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()//
				.sourceBucket(this.bucketName)//
				.sourceKey(sourceKey)//
				.destinationBucket(this.bucketName)//
				.destinationKey(destinationKey)//
				.build();

		this.s3Client.copyObject(copyObjectRequest);
	}
}