package com.example.BaseCMS.module.file.dto;

import lombok.Data;
import com.opencsv.bean.CsvBindByName;

@Data
public class ProductCsv {
    @CsvBindByName(column = "Tên sản phẩm")
    private String name;

    @CsvBindByName(column = "Slug")
    private String slug;

    @CsvBindByName(column = "Mô tả")
    private String description;

    @CsvBindByName(column = "Mô tả ngắn")
    private String shortDescription;

    @CsvBindByName(column = "Giá gốc")
    private Long price;

    @CsvBindByName(column = "Giá khuyến mãi")
    private Long discountPrice;

    @CsvBindByName(column = "Danh mục")
    private String category;

    @CsvBindByName(column = "Ảnh")
    private String imageUrl;

    @CsvBindByName(column = "Thương hiệu")
    private String brand;

    @CsvBindByName(column = "Tiêu đề SEO")
    private String seoTitle;

    @CsvBindByName(column = "Mô tả SEO")
    private String seoDescription;

    @CsvBindByName(column = "Từ khóa SEO")
    private String seoKeyword;

}
