package com.example.BaseCMS.module.auth.controller;

import com.example.BaseCMS.module.auth.request.LoginRequest;
import com.example.BaseCMS.module.auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.ok().build();
    }

}
