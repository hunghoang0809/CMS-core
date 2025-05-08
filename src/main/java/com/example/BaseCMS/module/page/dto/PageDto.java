package com.example.BaseCMS.module.page.dto;

import com.example.BaseCMS.module.page.PageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {
    private Long id;
    private String title;
    private String content;
    private PageEnum status;
    private Long authorId;
    private String authorName;
    private Long categoryId;
    private String categoryName;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
}
