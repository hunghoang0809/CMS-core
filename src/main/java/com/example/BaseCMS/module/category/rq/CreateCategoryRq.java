package com.example.BaseCMS.module.category.rq;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class CreateCategoryRq {
    private String name;
    private String slug;
    private long parentId;
    private String description;
    private String imageUrl;
}
