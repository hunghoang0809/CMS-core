package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN CategoryProduct cp on cp.productId = p.id "+
            "WHERE (cp.categoryId IS NULL OR cp.categoryId = :categoryId) " )
    Page<Product> findAllProduct(Pageable pageable, Long categoryId);


    @Query("""
    SELECT  p FROM Product p
    LEFT JOIN CategoryProduct cp ON cp.productId = p.id
    LEFT JOIN Category c ON c.id = cp.categoryId
    LEFT JOIN Category parent ON parent.id = c.parentId
    WHERE (
        (c.parentId IS NOT NULL) OR
        (p.id NOT IN (
            SELECT cp2.productId FROM CategoryProduct cp2
            JOIN Category c2 ON c2.id = cp2.categoryId
            WHERE c2.parentId IS NOT NULL
        ))
        AND (cp.categoryId IS NULL OR cp.categoryId = :categoryId)
    )
    ORDER BY
     CASE
        WHEN cp.categoryId IS NULL THEN 1
        ELSE 0
        END,
        COALESCE(c.name, parent.name),
        p.name
""")
    Page<Product> findProduct(Pageable pageable, Long categoryId);
}
