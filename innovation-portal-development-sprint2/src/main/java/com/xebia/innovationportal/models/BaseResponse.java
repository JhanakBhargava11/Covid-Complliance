package com.xebia.innovationportal.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BaseResponse<T> {

    private Long timestamp = System.currentTimeMillis();
    private int code = 200;
    private String message = "success";
    private String token;
    private T result;
    private List<T> data;

    public BaseResponse(T result) {
        this(null, result);
    }

    public BaseResponse(List<T> data) {
        this(null, data);
    }

    public BaseResponse(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse(String token, T result) {
        this("success", token, result);
    }

    public BaseResponse(String token, List<T> data) {
        this("success", token, data);
    }

    public BaseResponse(String message, String token, T result) {
        this(200, message, token, result);
    }

    public BaseResponse(String message, String token, List<T> data) {
        this(200, message, token, data);
    }

    public BaseResponse(int code, String message, String token, T result) {
        this.code = code;
        this.message = message;
        this.token = token;
        this.result = result;
    }

    public BaseResponse(int code, String message, String token, List<T> data) {
        this.code = code;
        this.message = message;
        this.token = token;
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
