package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p")
    Page<Product> findAllProduct(Pageable pageable);
}
