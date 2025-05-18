package com.example.BaseCMS.module.slide;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "slide")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Slide extends BaseEntity {
    private String imageUrl;

}
