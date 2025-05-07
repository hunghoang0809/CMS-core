package com.example.BaseCMS.module.product.model;

import com.example.BaseCMS.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product extends BaseEntity {
    private String name;
    private Long price;
    private String description;
    private String shortDescription;
    private String imageUrl;
    private Long discountPrice;
}
