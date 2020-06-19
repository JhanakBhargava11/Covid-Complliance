package com.xebia.innovationportal.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        ErrorDetails errorDetails = ErrorDetails.of(HttpStatus.NOT_FOUND, e.getMessage(), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IdeaNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleIdeaNotFoundException(IdeaNotFoundException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        ErrorDetails errorDetails = ErrorDetails.of(HttpStatus.NOT_FOUND, e.getMessage(), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    public ResponseEntity<ErrorDetails> handleRoleNotFoundException(RoleNotFoundException e) {
        List<String> details = new ArrayList<>();
        details.add(e.getLocalizedMessage());
        ErrorDetails errorDetails = ErrorDetails.of(HttpStatus.NOT_FOUND, e.getMessage(), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidRoleTypeException.class})
    public final ResponseEntity<Object> handleInvalidRoleTypeException(InvalidRoleTypeException u) {
        List<String> details = new ArrayList<>();
        details.add(u.getLocalizedMessage());
        ErrorDetails errorDetails = ErrorDetails.of(HttpStatus.BAD_REQUEST, u.getMessage(), details);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        // ErrorDetails errorDetails = ErrorDetails.of(new Date(), "Validation failed!",
        // details);
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
        return null;
    }

}
