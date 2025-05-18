package com.example.BaseCMS.module.slide;

import com.example.BaseCMS.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/slide")
@RequiredArgsConstructor
public class AdminSlideController {
    private final SlideService slideService;

    @Operation(summary = "Create slide")
    @PostMapping("")
    public ResponseEntity<?> create(SlideRq slideRq) {
         slideService.create(slideRq);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }

    @Operation(summary = "Get all slides")
    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", slideService.getAll()));
    }

    @Operation(summary = "Delete slide")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        slideService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }

}
