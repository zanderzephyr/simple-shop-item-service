package com.simple_shop.item_service.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item.dto.ConfirmUploadRequestDto;
import com.simple_shop.item_service.item.dto.DeleteResourceRequestDto;
import com.simple_shop.item_service.item.dto.RequestUploadUrlRequestDto;
import com.simple_shop.item_service.item.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item.model.DeleteResourceRequest;
import com.simple_shop.item_service.item.model.RequestUploadUrlRequest;
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
	public ResponseEntity<RequestUploadUrlResponseDto> requestImageUrl(//
			@PathVariable final long itemId, //
			@RequestBody final RequestUploadUrlRequestDto dtoIn) {

		final RequestUploadUrlRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		final RequestUploadUrlResponse modelOut = this.itemImageService.requestUploadUrl(itemId, modelIn);
		final RequestUploadUrlResponseDto dtoOut = ResourceDtoMapper.toDto(modelOut);
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoOut);
	}

	@PostMapping("/{itemId}/confirm-upload")
	public ResponseEntity<Void> confirmUpload(//
			@PathVariable final long itemId, //
			@RequestBody final ConfirmUploadRequestDto dtoIn) {

		final ConfirmUploadRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		this.itemImageService.confirmUpload(itemId, modelIn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@DeleteMapping("/{itemId}/delete-image")
	public ResponseEntity<Void> deleteImage(//
			@PathVariable final long itemId, //
			@RequestBody final DeleteResourceRequestDto dtoIn) {

		final DeleteResourceRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		this.itemImageService.deleteImage(itemId, modelIn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// TODO Get resources
}
