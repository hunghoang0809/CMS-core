package com.example.BaseCMS.module.page.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCategoryDto {
    private String name;
    private Long id;
    private String slug;
}
