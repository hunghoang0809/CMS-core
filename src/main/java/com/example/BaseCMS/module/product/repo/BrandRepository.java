package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBySlug(String slug);
    boolean existsBySlug(String slug);

}
