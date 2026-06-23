package com.simple_shop.item_service.item.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class ItemController {

	public ItemController() {
		super();
	}

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World! Item service is up and running!";
	}
}