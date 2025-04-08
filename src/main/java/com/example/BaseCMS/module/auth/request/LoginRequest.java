package com.example.BaseCMS.module.auth.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
