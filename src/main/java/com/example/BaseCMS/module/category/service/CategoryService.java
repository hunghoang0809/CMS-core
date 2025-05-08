package com.example.BaseCMS.module.category.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.category.dto.ListCategoryDto;
import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.category.rq.CreateCategoryRq;
import com.example.BaseCMS.module.product.model.CategoryProduct;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.repo.CategoryProductRepository;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import com.example.BaseCMS.module.product.rq.CreateProductRq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryProductRepository categoryProductRepository;

    @Transactional
    public Category createCategory(CreateCategoryRq rq) {
        if (categoryRepository.existsBySlug(rq.getSlug())) {
            throw new GenericErrorException("Slug đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        Category category = Category.builder()
                .name(rq.getName())
                .description(rq.getDescription())
                .imageUrl(rq.getImageUrl())
                .slug(rq.getSlug())
                .parentId(rq.getParentId())
                .build();
        categoryRepository.save(category);
        if (rq.getProductIds() != null && !rq.getProductIds().isEmpty()) {
            createCategoryProduct(rq, category);
        }

        return category;
    }

    @Transactional
    public void createCategoryProduct(CreateCategoryRq rq, Category category) {
        for (Long productId : rq.getProductIds()) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new GenericErrorException("Sản phẩm với id " + productId + " chưa được tạo", HttpStatus.BAD_REQUEST));
            CategoryProduct categoryProduct = CategoryProduct.builder()
                    .productId(product.getId())
                    .categoryId(category.getId())
                    .build();
            categoryProductRepository.save(categoryProduct);
        }
    }

    public Page<Category> getAllCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục với id " + id, HttpStatus.NOT_FOUND));
    }

    public void updateCategory(Long id, CreateCategoryRq rq) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục với id " + id, HttpStatus.NOT_FOUND));
        if (categoryRepository.existsBySlug(rq.getSlug())) {
            throw new GenericErrorException("Slug đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        category.setName(rq.getName());
        category.setDescription(rq.getDescription());
        category.setImageUrl(rq.getImageUrl());
        category.setSlug(rq.getSlug());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục với id " + id, HttpStatus.NOT_FOUND));
        categoryRepository.delete(category);
    }

    public List<ListCategoryDto> getAllGroupByParentId() {
       List<Category> categories = categoryRepository.findAll();
        return categories.stream()
               .filter(category -> category.getParentId() == 0)
               .map(category -> {
                    List<Category> subCategories = categories.stream()
                           .filter(subCategory -> subCategory.getParentId() == category.getId())
                           .toList();
                    return new ListCategoryDto(category.getId(), category.getName(), category.getSlug(), subCategories);
               })
               .toList();
    }

    public List<Category> getSubCategoriesByParentId(Long parentId) {
        categoryRepository.findById(parentId).orElseThrow(() -> new GenericErrorException("Không tìm thấy danh mục cha với id " + parentId, HttpStatus.NOT_FOUND));
        return categoryRepository.findByParentId(parentId);
    }
}
