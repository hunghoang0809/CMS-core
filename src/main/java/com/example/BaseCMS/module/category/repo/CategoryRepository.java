package com.example.BaseCMS.module.category.repo;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);

    List<Category> findByParentId(Long parentId);


}
