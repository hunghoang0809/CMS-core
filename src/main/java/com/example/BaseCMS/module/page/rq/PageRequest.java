package com.example.BaseCMS.module.page.rq;


import com.example.BaseCMS.module.page.PageEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private String title;
    private String content;
    private PageEnum status;
    private long categoryId;
}
