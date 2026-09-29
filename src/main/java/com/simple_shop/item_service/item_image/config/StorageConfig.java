package com.simple_shop.item_service.item_image.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class StorageConfig {
	@Value("${app.storage.endpoint}")
	private String endpoint;

	@Value("${aws.access-key-id}")
	private String accessKey;

	@Value("${aws.secret-access-key}")
	private String secretKey;

	@Value("${aws.region}")
	private String region;

	public StorageConfig() {
		super();
	}

	@Bean
	public S3Client s3Client() {
		return S3Client.builder()//
				// Remove when using actual AWS S3
				.endpointOverride(URI.create(this.endpoint))//
				.forcePathStyle(true)//

				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))//
				.region(Region.of(this.region))//
				.build();
	}

	@Bean
	public S3Presigner s3Presigner() {
		return S3Presigner.builder()
				// Remove when using actual AWS S3
				.endpointOverride(URI.create(this.endpoint))//
				.serviceConfiguration(S3Configuration.builder()//
						.pathStyleAccessEnabled(true)//
						.build())//

				.credentialsProvider(
						StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))//
				.region(Region.of(this.region))//
				.build();
	}
}