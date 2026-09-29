package com.simple_shop.item_service.item_image.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

// TODO move to common lib
@Component
public class StorageInitializer implements ApplicationRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(StorageInitializer.class);

	private final S3Client s3Client;

	private final String bucketName;

	public StorageInitializer(final S3Client s3Client, @Value("${app.storage.bucket-name}") final String bucketName) {
		super();

		this.s3Client = s3Client;
		this.bucketName = bucketName;
	}

	@Override
	public void run(final ApplicationArguments args) {
		StorageInitializer.LOGGER.debug("Checking if storage bucket '" + this.bucketName + "' exists...");

		try {
			final HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()//
					.bucket(this.bucketName)//
					.build();
			this.s3Client.headBucket(headBucketRequest);

			StorageInitializer.LOGGER.debug("Storage bucket '" + this.bucketName + "' already exists.");
		} catch (final S3Exception e) {
			if (e.statusCode() == HttpStatus.NOT_FOUND.value()) {
				StorageInitializer.LOGGER.debug("Bucket '" + this.bucketName + "' not found. Creating it now...");
				final CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()//
						.bucket(this.bucketName)//
						.build();
				this.s3Client.createBucket(createBucketRequest);
				StorageInitializer.LOGGER.debug("Storage bucket '" + this.bucketName + "' successfully created!");
			} else {
				StorageInitializer.LOGGER.error("Failed to connect or verify the storage bucket structure", e);
			}
		}
	}
}