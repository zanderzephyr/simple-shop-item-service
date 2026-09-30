package com.simple_shop.item_service.item_image.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.simple_shop.item_service.shared.utils.ItemServiceUtils;

@Component
public class ItemImageHousekeeper {
	private static final Logger LOGGER = LoggerFactory.getLogger(ItemImageHousekeeper.class);

	private static final long EXPIRATION_THRESHOLD_IN_HOURS = 2;

	private final ItemImageService itemImageService;

	public ItemImageHousekeeper(final ItemImageService itemImageService) {
		super();

		this.itemImageService = itemImageService;
	}

	/**
	 * Executes the housekeeping routine for expired pending uploads every hour.
	 */
	@Scheduled(cron = "0 0 * * * *")
	public void cleanAbandonedUploads() {
		ItemImageHousekeeper.LOGGER.debug("Starting scheduled item image housekeeping task...");

		try {
			final LocalDateTime expirationThreshold = ItemServiceUtils.createNow()
					.minusHours(ItemImageHousekeeper.EXPIRATION_THRESHOLD_IN_HOURS);

			this.itemImageService.cleanExpiredPendingImages(expirationThreshold);

			ItemImageHousekeeper.LOGGER.debug("Item image housekeeping task completed successfully.");
		} catch (final Exception e) {
			ItemImageHousekeeper.LOGGER.error("An unexpected error occurred during item image housekeeping!", e);
		}
	}
}