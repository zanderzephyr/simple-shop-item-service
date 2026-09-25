package com.simple_shop.item_service.item.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

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
						StaticCredentialsProvider.create(AwsBasicCredentials.create(this.accessKey, this.secretKey)))
				.region(Region.of(this.region))//
				.build();
	}
}