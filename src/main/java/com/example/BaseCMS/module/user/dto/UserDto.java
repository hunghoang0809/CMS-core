package com.example.BaseCMS.module.user.dto;

import lombok.Data;

@Data

public class UserDto {
    private Long id;
    private String username;
    private String password;

    public UserDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
