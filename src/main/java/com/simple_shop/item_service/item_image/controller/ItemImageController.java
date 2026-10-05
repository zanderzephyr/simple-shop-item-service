package com.simple_shop.item_service.item_image.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple_shop.item_service.item_image.dto.ConfirmUploadRequestDto;
import com.simple_shop.item_service.item_image.dto.DeleteResourceRequestDto;
import com.simple_shop.item_service.item_image.dto.RequestResourcesResponseDto;
import com.simple_shop.item_service.item_image.dto.RequestUploadUrlRequestDto;
import com.simple_shop.item_service.item_image.dto.RequestUploadUrlResponseDto;
import com.simple_shop.item_service.item_image.model.ConfirmUploadRequest;
import com.simple_shop.item_service.item_image.model.DeleteResourceRequest;
import com.simple_shop.item_service.item_image.model.RequestResourcesResponse;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlRequest;
import com.simple_shop.item_service.item_image.model.RequestUploadUrlResponse;
import com.simple_shop.item_service.item_image.service.ItemImageService;

@RestController
@RequestMapping("/api/items/v1/images")
public class ItemImageController {
	private final ItemImageService itemImageService;

	public ItemImageController(final ItemImageService itemImageService) {
		super();

		this.itemImageService = itemImageService;
	}

	@PostMapping("/request-image-url/{itemId}")
	public ResponseEntity<RequestUploadUrlResponseDto> requestImageUrl(//
			@PathVariable final long itemId, //
			@RequestBody final RequestUploadUrlRequestDto dtoIn) {

		final RequestUploadUrlRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		final RequestUploadUrlResponse modelOut = this.itemImageService.requestUploadUrl(itemId, modelIn);
		final RequestUploadUrlResponseDto dtoOut = ResourceDtoMapper.toDto(modelOut);
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoOut);
	}

	@PostMapping("/confirm-upload/{itemId}")
	public ResponseEntity<Void> confirmUpload(//
			@PathVariable final long itemId, //
			@RequestBody final ConfirmUploadRequestDto dtoIn) {

		final ConfirmUploadRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		this.itemImageService.confirmUpload(itemId, modelIn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// POST instead of DELETE: DELETE usually has no body, POST does.
	@PostMapping("/delete-image/{itemId}")
	public ResponseEntity<Void> deleteImage(//
			@PathVariable final long itemId, //
			@RequestBody final DeleteResourceRequestDto dtoIn) {

		final DeleteResourceRequest modelIn = ResourceDtoMapper.toDomain(dtoIn);
		this.itemImageService.deleteImage(itemId, modelIn);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@GetMapping("/get-images/{itemId}")
	public ResponseEntity<RequestResourcesResponseDto> getImages(@PathVariable final long itemId) {
		final RequestResourcesResponse modelOut = this.itemImageService.getImages(itemId);
		final RequestResourcesResponseDto dtoOut = ResourceDtoMapper.toDto(modelOut);
		return ResponseEntity.status(HttpStatus.OK).body(dtoOut);
	}
}
