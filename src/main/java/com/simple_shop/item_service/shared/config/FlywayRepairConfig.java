package com.simple_shop.item_service.shared.config;

import org.springframework.boot.flyway.autoconfigure.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

// Only activate when repairs are needed
// @Component
public class FlywayRepairConfig {
	public FlywayRepairConfig() {
		super();
	}

	@Bean
	public FlywayMigrationStrategy cleanMigrateStrategy() {
		return flyway -> {
			flyway.repair();
			flyway.migrate();
		};
	}
}