package com.xebia.innovationportal.exceptions;

import org.springframework.http.HttpStatus;

public class GenericException extends RuntimeException {

    private int code;
    private final Long timestamp;

    public GenericException(String message) {
        this(message, 500);
    }

    public GenericException(String message, int code) {
        super(message);
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    public GenericException(HttpStatus httpStatus) {
        super(httpStatus.getReasonPhrase());
        this.code = httpStatus.value();
        this.timestamp = System.currentTimeMillis();
    }

    public int getCode() {
        return code;
    }

    public Long getTimestamp() {
        return timestamp;
    }

}
