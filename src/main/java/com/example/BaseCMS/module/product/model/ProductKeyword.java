package com.example.BaseCMS.module.product.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "keyword_product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductKeyword extends BaseEntity {
    private Long productId;
    private Long keywordId;
}
