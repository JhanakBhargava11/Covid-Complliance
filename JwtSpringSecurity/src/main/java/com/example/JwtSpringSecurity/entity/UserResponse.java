package com.example.JwtSpringSecurity.entity;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
        private HttpStatus status;
        private String message;
        private List<String> payload;

    public List<String> getPayload() {
        return payload;
    }

    public void setPayload(List<String> payload) {
        this.payload = payload;
    }

    public HttpStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
            this.message = message;
        }

    }
