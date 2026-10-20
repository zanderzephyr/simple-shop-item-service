package com.simple_shop.item_service.shared.dto;

import java.time.Instant;

public final class ServiceErrorResponseDto {
	private Instant timestamp;

	private String message;

	private String detail;

	private int httpState;

	public ServiceErrorResponseDto() {
		super();
	}

	public Instant getTimestamp() {
		return this.timestamp;
	}

	public String getMessage() {
		return this.message;
	}

	public String getDetail() {
		return this.detail;
	}

	public int getHttpState() {
		return this.httpState;
	}
}