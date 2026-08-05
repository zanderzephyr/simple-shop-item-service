package com.simple_shop.item_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public final class ItemServiceApplication {
	public ItemServiceApplication() {
		super();
	}

	public static void main(final String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}
}
