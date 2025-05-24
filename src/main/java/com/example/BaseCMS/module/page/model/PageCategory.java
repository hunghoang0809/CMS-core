package com.example.BaseCMS.module.page.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "page_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageCategory extends BaseEntity {
    private Long pageId;
    private Long categoryId;
}
