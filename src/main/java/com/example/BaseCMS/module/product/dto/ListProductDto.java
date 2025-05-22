package com.example.BaseCMS.module.product.dto;


import com.example.BaseCMS.module.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
public class ListProductDto {
    private Page<Product> products;
    private String categoryName;
    private Long categoryId;
    private String categorySlug;
}
