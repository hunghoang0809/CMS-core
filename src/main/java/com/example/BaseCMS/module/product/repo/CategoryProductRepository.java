package com.example.BaseCMS.module.product.repo;

import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {

    public boolean existsCategoryProductByCategoryIdAndProductId(Long categoryId, Long productId);

    public void deleteAllByProductId(Long productId);

    boolean existsByCategoryIdAndProductId(Long categoryId, Long productId);

    public List<CategoryProduct> findByProductId(Long productId);
}
