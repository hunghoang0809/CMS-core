package com.example.BaseCMS.module.category.repo;

import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsBySlug(String slug);

   Optional<Category> findByNameAndParentId(String name, Long parentId);




    List<Category> findByParentId(Long parentId);

    Optional<Category> findBySlug(String slug);

    @Query("SELECT c FROM Category c WHERE c.parentId = 0 order by c.name ASC ")
    List<Category> findAllParentCategory();


    @Query("SELECT c FROM Category c " +
            "ORDER BY c.createdAt DESC")
    Page<Category> findAll(Pageable pageable);

}
