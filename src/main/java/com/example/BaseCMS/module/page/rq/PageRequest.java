package com.example.BaseCMS.module.page.rq;


import com.example.BaseCMS.module.page.PageEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    @NotEmpty(message = "Tên trang không được để trống")
    private String title;
    @NotEmpty(message = "Nội dung không được để trống")
    private String content;
    @NotEmpty(message = "Trạng thái không được để trống")
    private PageEnum status;
    private String imgUrl;
    private String shortDescription;
    private List<Long> categoryId;
    private List<Long> keywordId;
    @NotEmpty(message = "Slug không được để trống")
    private String slug;
    private String seoTitle;
    private String seoDescription;
}
