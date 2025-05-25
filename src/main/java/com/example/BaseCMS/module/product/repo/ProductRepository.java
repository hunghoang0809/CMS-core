package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT DISTINCT p FROM Product p " +
            "INNER JOIN CategoryProduct cp on cp.productId = p.id "+
            "WHERE (:categoryId IS NULL OR cp.categoryId = :categoryId) " )
    Page<Product> findAllProduct( Long categoryId, Pageable pageable);

    Product findBySlug(String slug);

    boolean existsBySlug(String slug);

    @Query(
            "SELECT p FROM Product p " +
                    "LEFT JOIN CategoryProduct cp ON cp.productId = p.id " +
                    "LEFT JOIN ProductKeyword pk ON p.id = pk.productId " +
                    "LEFT JOIN Keyword k ON k.id = pk.keywordId " +
                    "LEFT JOIN Category c ON c.id = cp.categoryId " +
                    "WHERE (:categoryId IS NULL OR cp.categoryId = :categoryId) " +
                    "AND (:brandId IS NULL OR p.brandId = :brandId) " +
                    "AND (:keyword IS NULL OR (" +
                    "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
                    "OR LOWER(k.name) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
                    ")) " +
                    "ORDER BY p.createdAt DESC"
    )
    Page<Product> findProduct(Long categoryId, Long brandId, String keyword,Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN CategoryProduct cp ON cp.productId = p.id " +
            "WHERE cp.categoryId = :categoryId "

    )
    Page<Product> findProductByCategoryId(Long categoryId, Pageable pageable);

    @Query("Select p.slug from Product p")
    Set<String> findAllSlug();

}
