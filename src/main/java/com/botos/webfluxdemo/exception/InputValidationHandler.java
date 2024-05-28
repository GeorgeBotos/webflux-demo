package com.botos.webfluxdemo.exception;

import com.botos.webfluxdemo.dto.InputFailedValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

	@ExceptionHandler(InputValidationException.class)
	public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException exception) {
		var response = new InputFailedValidationResponse(InputValidationException.getErrorCode(),
		                                                 exception.getInput(),
		                                                 exception.getMessage());
		return ResponseEntity.badRequest()
		                     .body(response);
	}
}
