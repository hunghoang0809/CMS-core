package com.example.BaseCMS.module.page.rq;


import com.example.BaseCMS.module.page.PageEnum;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long categoryId;
    @NotEmpty(message = "Slug không được để trống")
    private String slug;
    private String seoTitle;
    private String seoDescription;
}
