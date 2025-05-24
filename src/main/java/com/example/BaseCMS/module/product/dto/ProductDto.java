package com.example.BaseCMS.module.product.dto;


import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String imageUrl;
    private Long price;
    private String slug;
    private Long discountPrice;
    private String description;
    private String shortDescription;
    private List<ProductCategoryDto> productCategories;
    private List<ProductKeywordDto> productKeywords;
    private String seoTitle;
    private String seoDescription;
 }
