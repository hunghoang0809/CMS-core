package com.example.BaseCMS.module.category.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping("")
    public ResponseEntity<?> getAllCategories() {
        return  ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.getAllGroupByParentId()));
    }

    @Operation(summary = "Get all subcategories")
    @GetMapping("/sub-categories/{parent-id}")
    public ResponseEntity<?> getSubCategories(@PathVariable("parent-id") Long parentId) {
        return  ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.getSubCategoriesByParentId(parentId)));
    }

    @Operation(summary = "Get category by slug")
    @GetMapping("/{slug}")
    public ResponseEntity<?> getCategoryBySlug(@PathVariable("slug") String slug) {
        return  ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.getCategoryBySlug(slug)));
    }
}
