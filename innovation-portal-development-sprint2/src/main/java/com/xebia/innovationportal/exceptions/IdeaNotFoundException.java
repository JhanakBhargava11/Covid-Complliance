package com.xebia.innovationportal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IdeaNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public IdeaNotFoundException(String message) {
		super(message);
	}

}
