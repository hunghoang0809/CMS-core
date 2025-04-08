package com.example.BaseCMS.module.auth.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public String login(String username, String password) {
        // Implement your login logic here
        return "Login successful for user: " + username;
    }

    public String register(String username, String password) {
        // Implement your registration logic here
        return "Registration successful for user: " + username;
    }
}
