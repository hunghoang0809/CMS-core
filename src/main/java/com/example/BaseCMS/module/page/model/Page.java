
package com.example.BaseCMS.module.page.model;

import com.example.BaseCMS.common.BaseEntity;
import com.example.BaseCMS.module.page.PageEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "page")
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Page extends BaseEntity {
    private String title;
    private String content;
    private long authorId;
    @Enumerated(EnumType.ORDINAL)
    private PageEnum status;
    private String imageUrl;
    private String slug;
    private String shortDescription;
    private String seoTitle;
    private String seoDescription;
}
