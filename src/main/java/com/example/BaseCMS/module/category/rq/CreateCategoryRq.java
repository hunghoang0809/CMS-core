package com.example.BaseCMS.module.category.rq;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CreateCategoryRq {
    @NotEmpty(message = "Tên danh mục không được để trống")
    private String name;
    @NotEmpty(message = "Slug danh mục không được để trống")
    private String slug;
    private long parentId;
    private String description;
    private String imageUrl;
    private List<Long> productIds;
}
