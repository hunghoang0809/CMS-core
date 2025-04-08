package com.example.BaseCMS.module.auth.controller;

import com.example.BaseCMS.module.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody String username, @RequestBody String password) {
        authService.login(username, password);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register() {
        return ResponseEntity.ok().build();
    }

}
