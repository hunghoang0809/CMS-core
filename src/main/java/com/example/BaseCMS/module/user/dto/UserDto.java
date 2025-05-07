package com.example.BaseCMS.module.user.dto;

import lombok.Data;

@Data

public class UserDto {
    private long id;
    private String username;
    private String password;

    public UserDto(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
