package com.example.BaseCMS.module.category.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category extends BaseEntity {
   private String name;
   private String slug;
   private String description;
    private String imageUrl;
   private long parentId;
}
