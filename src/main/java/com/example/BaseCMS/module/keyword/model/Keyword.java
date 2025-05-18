package com.example.BaseCMS.module.keyword.model;


import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "keyword")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Keyword extends BaseEntity {
    private String name;
}
