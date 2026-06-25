package com.simple_shop.item_service.item.exceptions;

public final class InvalidPageSetupException extends IllegalArgumentException {
	private static final long serialVersionUID = 3126015455269952391L;

	public InvalidPageSetupException() {
		super();
	}

	public InvalidPageSetupException(final String message) {
		super(message);
	}

	public InvalidPageSetupException(final Throwable throwable) {
		super(throwable);
	}

	public InvalidPageSetupException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
