package com.example.BaseCMS.module.product.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Get all products grouped by category")
    @GetMapping("/grouped-by-category")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId
            ) {
        Pageable pageable = Pageable.ofSize(size).withPage(0);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", productService.getAll(pageable)));
    }

    @Operation(summary = "Get all products")
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "brandId", required = false) Long brandId
            ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", productService.getAllProduct(pageable, categoryId, brandId)));
    }

    @Operation(summary = "Get product by slug")
    @GetMapping("/{slug}")
    public ResponseEntity<?> getProductBySlug(@PathVariable("slug") String slug) {
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", productService.getProductBySlug(slug)));
    }


}
