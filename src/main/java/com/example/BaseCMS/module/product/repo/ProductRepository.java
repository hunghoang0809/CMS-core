package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN CategoryProduct cp on cp.productId = p.id "+
            "WHERE (:categoryId IS NULL OR cp.categoryId = :categoryId) " )
    Page<Product> findAllProduct(Pageable pageable, Long categoryId);

    Product findBySlug(String slug);


    @Query(
    "SELECT  p FROM Product p " +
    "LEFT JOIN CategoryProduct cp ON cp.productId = p.id " +
    "LEFT JOIN Category c ON c.id = cp.categoryId " +
    "WHERE :categoryId IS NULL OR cp.categoryId = :categoryId " + "AND :brandId IS NULL OR p.brandId = :brandId"
    )
    Page<Product> findProduct(Pageable pageable, Long categoryId, Long brandId);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN CategoryProduct cp ON cp.productId = p.id " +
            "WHERE cp.categoryId = :categoryId "

    )
    Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable);

}
