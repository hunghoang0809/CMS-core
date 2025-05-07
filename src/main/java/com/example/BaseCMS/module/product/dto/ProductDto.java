package com.example.BaseCMS.module.product.dto;


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
    private List<Long> categoryId;
 }
