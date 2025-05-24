package com.example.BaseCMS.module.product.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.product.rq.CreateProductRq;
import com.example.BaseCMS.module.product.rq.ProductFilterRq;
import com.example.BaseCMS.module.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/product")
@RequiredArgsConstructor
public class AdminProductController {
    private final ProductService productService;

    @Operation(summary = "Create product")
    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody()CreateProductRq rq) {
        productService.create(rq);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", null));
    }

    @Operation(summary = "Update product")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable long id,
            @RequestBody()CreateProductRq rq) {
        productService.update(id, rq);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", null));
    }

    @Operation(summary = "Get product by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", productService.getById(id)));
    }

    @Operation(summary = "Get all products")
    @GetMapping("")
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "categoryId", required = false) Long categoryId){

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", productService.list(pageable, categoryId)));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return  ResponseEntity.ok(
                new ApiResponse<>(200, "Success", null));
    }
}
