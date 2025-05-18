package com.example.BaseCMS.module.product.dto;


import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.page.dto.PageCategoryDto;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private String id;
    private String name;
    private String imageUrl;
    private Long price;
    private Long discountPrice;
    private String shortDescription;
    private List<ProductCategoryDto> productCategories;
    private List<ProductKeywordDto> productKeywords;
    private List<Long> keywordIds;
    private List<String> keywordNames;
    private String seoTitle;
    private String seoDescription;
 }
