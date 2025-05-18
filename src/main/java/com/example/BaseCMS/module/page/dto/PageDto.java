package com.example.BaseCMS.module.page.dto;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.page.PageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private String shortDescription;
    private String slug;
    private PageEnum status;
    private Long authorId;
    private String authorName;
    private List<PageCategoryDto> categories;
    private List<PageKeywordDto> keywords;
    private String seoTitle;
    private String seoDescription;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
