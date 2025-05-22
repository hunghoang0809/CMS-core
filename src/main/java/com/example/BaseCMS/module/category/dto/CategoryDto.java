package com.example.BaseCMS.module.category.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private long parentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CategoryDto(Long id, String name, String slug, String description, String imageUrl, long parentId) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.imageUrl = imageUrl;
        this.parentId = parentId;
    }
}
