package com.example.BaseCMS.module.product.rq;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CreateProductRq {
    private String name;
    private String description;
    private String shortDescription;
    private List<Long> categoryId;
    private Long price;
    private String imageUrl;
    private Long discountPrice;
}
