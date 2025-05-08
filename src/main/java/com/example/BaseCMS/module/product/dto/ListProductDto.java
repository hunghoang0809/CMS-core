package com.example.BaseCMS.module.product.dto;

import com.example.BaseCMS.module.category.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ListProductDto {
    private long id;
    private String name;
    private String shortDescription;
    private String imageUrl;
}
