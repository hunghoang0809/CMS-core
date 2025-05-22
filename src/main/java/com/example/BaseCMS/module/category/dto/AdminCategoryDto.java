package com.example.BaseCMS.module.category.dto;

import lombok.Data;

@Data
public class AdminCategoryDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imgUrl;
    private Long parentId;
    private String parentName;
    private String createdAt;
    private String updatedAt;
}
