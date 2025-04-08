package com.example.BaseCMS.module.auth.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public void login(String username, String password) {

    }

    public String register(String username, String password) {

        return "Registration successful for user: " + username;
    }
}
