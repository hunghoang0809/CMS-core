package com.example.BaseCMS.module.product.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.product.rq.CreateBrandRq;
import com.example.BaseCMS.module.product.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/brands")
@RequiredArgsConstructor
public class AdminBrandController {
    private final BrandService brandService;

    @Operation(summary = "Create brand")
    @PostMapping("")
    public ResponseEntity<?> createBrand(@RequestBody() CreateBrandRq rq) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.createBrand(rq)));
    }
    @Operation(summary = "Update brand")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable("id") Long id, @RequestBody() CreateBrandRq rq) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.updateBrand(id, rq)));
    }
    @Operation(summary = "Delete brand")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable("id") Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }

    @Operation(summary = "Get all brands")
    @GetMapping("")
    public ResponseEntity<?> getAllBrands(@RequestParam(value = "page", defaultValue = "0") int page,
                                          @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.getAllBrands(pageable)));
    }

    @Operation(summary = "Get brand by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", brandService.findById(id)));
    }
}
