package com.example.BaseCMS.module.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private long id;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
