package com.example.BaseCMS.module.product.rq;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CreateProductRq {
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotEmpty(message = "Mô tả sản phẩm không được để trống")
    private String description;
    private String shortDescription;
    private List<Long> categoryId;
    @NotEmpty(message = "Giá sản phẩm không được để trống")
    private Long price;
    private String imageUrl;
    private Long discountPrice;
}
