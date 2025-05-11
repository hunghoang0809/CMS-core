package com.example.BaseCMS.module.product.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "Get brand by slug")
    @GetMapping("/{slug}")
    public ResponseEntity<?> getBrandBySlug(@PathVariable("slug") String slug) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.findBySlug(slug)));
    }

    @Operation(summary = "Get all brands")
    @GetMapping("")
    public ResponseEntity<?> getAllBrands() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.getAllBrands()));
    }
}
