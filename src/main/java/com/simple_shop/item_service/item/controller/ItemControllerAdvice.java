package com.simple_shop.item_service.item.controller;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.simple_shop.item_service.item.dto.ItemServiceErrorResponseDto;
import com.simple_shop.item_service.item.dto.ItemServiceErrorResponseDto.ItemServiceErrorResponseDtoBuilder;
import com.simple_shop.item_service.item.exceptions.InvalidMessageException;
import com.simple_shop.item_service.item.exceptions.ItemNotFoundException;

@RestControllerAdvice(assignableTypes = ItemController.class)
public final class ItemControllerAdvice {

	public ItemControllerAdvice() {
		super();
	}

	@ExceptionHandler(InvalidMessageException.class)
	public ResponseEntity<ItemServiceErrorResponseDto> handleInvalidRequestException(
			final InvalidMessageException exception) {

		final ItemServiceErrorResponseDto responseDto = new ItemServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Invalid request")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}

	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<ItemServiceErrorResponseDto> handleItemNotFoundException(
			final ItemNotFoundException exception) {

		final ItemServiceErrorResponseDto responseDto = new ItemServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.NOT_FOUND.value())//
				.setMessage("Item not found")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
	}

	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ItemServiceErrorResponseDto> handleObjectOptimisticLockingFailureException(
			final ObjectOptimisticLockingFailureException exception) {

		final ItemServiceErrorResponseDto responseDto = new ItemServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.CONFLICT.value())//
				.setMessage("Trying to work with a stale version")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ItemServiceErrorResponseDto> handleHttpMessageNotReadableException(
			final HttpMessageNotReadableException exception) {

		final ItemServiceErrorResponseDto responseDto = new ItemServiceErrorResponseDtoBuilder()//
				.setDetail(exception.getMessage())//
				.setHttpState(HttpStatus.BAD_REQUEST.value())//
				.setMessage("Request contains bad content!")//
				.setTimestamp(Instant.now())//
				.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
	}
}
