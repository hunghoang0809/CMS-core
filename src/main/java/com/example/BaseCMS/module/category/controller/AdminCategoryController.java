package com.example.BaseCMS.module.category.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.category.rq.CreateCategoryRq;
import com.example.BaseCMS.module.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create category")
    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody() CreateCategoryRq rq) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.createCategory(rq)));
    }

    @Operation(summary = "Get all categories")
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.getAllCategory(pageable)));
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", categoryService.getCategoryById(id)));
    }

    @Operation(summary = "Update category")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody() CreateCategoryRq rq) {
        categoryService.updateCategory(id, rq);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }


}
