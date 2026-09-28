package com.simple_shop.item_service.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.item.service.ItemImageService;

@RestController
@RequestMapping("/api/items/v1/images")
public class ItemImageController {
	private final ItemImageService itemImageService;

	public ItemImageController(final ItemImageService itemImageService) {
		super();

		this.itemImageService = itemImageService;
	}

	@PostMapping("/{itemId}/request-image-url")
	public ResponseEntity<RequestUploadUrlResponseDto> requestImageUrl(@PathVariable final long itemId) {
		final RequestUploadUrlResponse modelOut = this.itemImageService.requestUploadUrl(itemId);
		final RequestUploadUrlResponseDto dtoOut = ResourceDtoMapper.toDto(modelOut);
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoOut);
	}

	// TODO Confirm resource upload
	// TODO Delete resource
	// TODO Get resources
}
