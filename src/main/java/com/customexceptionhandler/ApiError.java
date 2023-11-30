package com.customexceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

	private HttpStatus httpStatus;
	private Object exception;
	private String message;
	private Object errors;

	private LocalDateTime currentTimestamp = LocalDateTime.now();

	public ApiError(HttpStatus httpStatus, Object exception, String message, Object errors) {
		this.httpStatus = httpStatus;
		this.exception = exception.getClass().getCanonicalName();
		this.message = message;
		this.errors = errors;
	}

}