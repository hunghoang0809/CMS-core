package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBySlug(String slug);
    @Query("SELECT b FROM Brand b WHERE b.slug = :slug")
    Optional<Brand> getBySlug(String slug);
    boolean existsBySlug(String slug);

}
