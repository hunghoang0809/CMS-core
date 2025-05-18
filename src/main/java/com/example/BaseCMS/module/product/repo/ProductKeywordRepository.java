package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.ProductKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {
    List<ProductKeyword> findByProductId(Long productId);

}
