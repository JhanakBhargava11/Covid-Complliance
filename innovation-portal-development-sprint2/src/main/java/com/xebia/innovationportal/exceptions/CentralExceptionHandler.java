package com.xebia.innovationportal.exceptions;

import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xebia.innovationportal.models.BaseResponse;

@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(GenericException.class)
	public final ResponseEntity<BaseResponse<Void>> handleAllExceptions(GenericException ex, WebRequest request) {
		BaseResponse<Void> response = new BaseResponse<>((Void) null);
		response.setCode(ex.getCode());
		response.setMessage(ex.getMessage());
		return ResponseEntity.status(ex.getCode()).body(response);
	}

	@ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
	public final ResponseEntity<Object> handleUserNotFoundException(UsernameNotFoundException u,
			BadCredentialsException b) {
		BaseResponse<Void> response = new BaseResponse<>(Collections.emptyList());
		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(HttpStatus.BAD_REQUEST.name());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BaseResponse<Void> response = new BaseResponse<>(Collections.emptyList());
		response.setCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(HttpStatus.BAD_REQUEST.name());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

}