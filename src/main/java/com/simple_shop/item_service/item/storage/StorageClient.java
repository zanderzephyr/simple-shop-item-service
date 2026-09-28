package com.simple_shop.item_service.item.storage;

// TODO Move to common lib
public interface StorageClient {
	/**
	 * Generates a temporary cryptographic pre-signed URL for direct HTTP PUT
	 * uploads.
	 *
	 * @param  storageKey The relative storage path within the bucket.
	 *
	 * @return            The absolute pre-signed URL string for direct client
	 *                    uploads.
	 */
	String generatePreSignedUploadUrl(final String storageKey);
}