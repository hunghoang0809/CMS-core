package com.example.BaseCMS.module.user.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.user.rq.UserRq;
import com.example.BaseCMS.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @Operation(summary = "Create a new user")
    @PostMapping("/")
    public ResponseEntity<?> createUser(@RequestBody UserRq userRq) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", userService.create(userRq)));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", userService.getAll(pageable)));
    }
}
