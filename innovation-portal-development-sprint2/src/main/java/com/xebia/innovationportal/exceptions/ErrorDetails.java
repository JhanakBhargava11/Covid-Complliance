package com.xebia.innovationportal.exceptions;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetails {
	private HttpStatus status;
	private Date timestamp;
	private String message;
	private List<String> details;

	private ErrorDetails() {
		timestamp = new Date();
	}

	private ErrorDetails(HttpStatus status) {
		this();
		this.status = status;

	}

	private ErrorDetails(HttpStatus status, String message, List<String> details) {
		this();
		this.status = status;
		this.details = details;
		this.message = message;
	}

	public static ErrorDetails of(HttpStatus status, String message, List<String> details) {
		return new ErrorDetails(status, message, details);
	}

}