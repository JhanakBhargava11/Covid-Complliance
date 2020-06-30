package com.example.JwtSpringSecurity.service;

import org.springframework.stereotype.Service;

@Service
public class TempUsername {
    private String username;

    public TempUsername() {
    }

    public TempUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
