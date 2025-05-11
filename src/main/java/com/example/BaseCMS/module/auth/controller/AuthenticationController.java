package com.example.BaseCMS.module.auth.controller;


import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.auth.dto.AuthenticationResponse;
import com.example.BaseCMS.module.auth.dto.AuthenticationRequest;
import com.example.BaseCMS.module.auth.service.AuthenticationService;
import com.example.BaseCMS.module.user.service.CustomUserDetails;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success",authenticationService.authenticate(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", authenticationService.getMe()));
    }
}