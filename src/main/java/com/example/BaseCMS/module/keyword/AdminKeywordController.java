package com.example.BaseCMS.module.keyword;

import com.example.BaseCMS.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/keywords")
@RequiredArgsConstructor
public class AdminKeywordController {
    private final KeywordService keywordService;


    @Operation(summary = "Create keyword")
    @PostMapping("")
    public ResponseEntity<?> createKeyword(@RequestBody() KeywordRq rq) {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", keywordService.create(rq.getName()))
        );
    }

    @Operation(summary = "Get all keywords")
    @GetMapping("")
    public ResponseEntity<?> getAllKeywords(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", keywordService.getAll(pageable))
        );
    }

    @Operation(summary = "Get keyword by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getKeywordById(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", keywordService.getById(id))
        );
    }

    @Operation(summary = "Update keyword")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateKeyword(
            @PathVariable Long id,
            @RequestBody() KeywordRq rq) {
        keywordService.update(id, rq.getName());
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", null)
        );
    }

    @Operation(summary = "Delete keyword")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKeyword(@PathVariable Long id) {
        keywordService.delete(id);
        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", null)
        );
    }


}
