package com.example.BaseCMS.module.category.dto;

import com.example.BaseCMS.module.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ListCategoryDto {
    private Long id;
    private String name;
    private String slug;
    private List<Category> subCategories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
