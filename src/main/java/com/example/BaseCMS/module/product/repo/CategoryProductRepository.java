package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {

    public boolean existsCategoryProductByCategoryIdAndProductId(Long categoryId, Long productId);
}
