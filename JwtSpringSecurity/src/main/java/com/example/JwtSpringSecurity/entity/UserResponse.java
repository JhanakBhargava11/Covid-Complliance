package com.example.JwtSpringSecurity.entity;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private String status;
    private String message;
    private Payload payload;

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
