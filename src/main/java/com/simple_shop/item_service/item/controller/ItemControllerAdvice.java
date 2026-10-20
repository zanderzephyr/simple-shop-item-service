package com.simple_shop.item_service.item.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;
import com.simple_shop.item_service.shared.dto.ServiceErrorResponseDto;

@RestControllerAdvice(assignableTypes = ItemController.class)
public final class ItemControllerAdvice {

	public ItemControllerAdvice() {
		super();
	}

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ServiceErrorResponseDto> handleItemNotFoundException(final ItemNotFoundException exception) {

		final ServiceErrorResponseDto responseDto = new ServiceErrorResponseDto()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.NOT_FOUND.value())//
				.setMessage("Item not found")//
				.setTimestamp(Instant.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}
}
