package com.simple_shop.item_service.item_image.storage;

import java.util.List;

// TODO Move to common lib
// TODO For more secure downloads, presigned download urls could be used.
public interface StorageClient {
	/**
	 * Generates a temporary cryptographic pre-signed URL for direct HTTP PUT
	 * uploads.
	 *
	 * @param  storageKey      The relative storage path within the bucket.
	 * @param  contentType     The type of the content to be uploaded.
	 * @param  fileSizeInBytes The exact content length in bytes to lock into the
	 *                         signature.
	 *
	 * @return                 The absolute pre-signed URL string for direct client
	 *                         uploads.
	 */
	String generatePreSignedUploadUrl(final String storageKey, final String contentType, final long fileSizeInBytes);

	/**
	 * Checks if an object exists at the storage key path.
	 */
	boolean doesObjectExist(final String storageKey);

	/**
	 * Copies an object from a source key path to a destination key path.
	 */
	void copyObject(final String sourceKey, final String destinationKey);

	/**
	 * Deletes an object at the storage key path.
	 */
	void deleteObject(final String storageKey);

	/**
	 * Deletes a bulk of objects at the storage key paths via bulk request.
	 *
	 * @param storageKeys The storage key paths.
	 */
	void deleteObjects(final List<String> storageKeys);
}