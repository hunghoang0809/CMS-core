package com.example.BaseCMS.module.category.service;

import com.example.BaseCMS.exc.GenericErrorException;
import com.example.BaseCMS.module.category.model.Category;
import com.example.BaseCMS.module.category.repo.CategoryRepository;
import com.example.BaseCMS.module.category.rq.CreateCategoryRq;
import com.example.BaseCMS.module.product.model.Product;
import com.example.BaseCMS.module.product.repo.ProductRepository;
import com.example.BaseCMS.module.product.rq.CreateProductRq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public Category createCategory(CreateCategoryRq rq) {
        if (categoryRepository.existsBySlug(rq.getSlug())) {
            throw new GenericErrorException("Slug đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        Category category = Category.builder()
                .name(rq.getName())
                .description(rq.getDescription())
                .imageUrl(rq.getImageUrl())
                .slug(rq.getSlug())
                .build();
        categoryRepository.save(category);

        return category;
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
}
