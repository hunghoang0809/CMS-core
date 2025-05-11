package com.example.BaseCMS.module.category.repo;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);

    List<Category> findByParentId(Long parentId);

    Optional<Category> findBySlug(String slug);

    @Query("SELECT c FROM Category c WHERE c.parentId = 0")
    List<Category> findAllParentCategory();

}
