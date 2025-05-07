package com.example.BaseCMS.module.product.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "category_product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryProduct extends BaseEntity {
    private Long categoryId;
    private Long productId;
}
