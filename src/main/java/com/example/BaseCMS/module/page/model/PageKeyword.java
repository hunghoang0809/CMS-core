package com.example.BaseCMS.module.page.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "page_keyword")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageKeyword extends BaseEntity {
    private Long pageId;
    private Long keywordId;
}
