package com.botos.webfluxdemo.exception;

import lombok.Getter;

public class InputValidationException extends RuntimeException {

	private static final String MESSAGE = "allowed range is 10 - 20";
	@Getter
	private static final int errorCode = 100;
	@Getter
	private final int input;

	public InputValidationException(int input) {
		super(MESSAGE);
		this.input = input;
	}
}
