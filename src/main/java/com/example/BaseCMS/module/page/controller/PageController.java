package com.example.BaseCMS.module.page.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.page.service.PageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages")
@RequiredArgsConstructor
public class PageController {
     private final PageService pageService;
     @Operation(summary = "Get all pages")
     @GetMapping("/")
     public ResponseEntity<?> getAllPages(
             @RequestParam(value = "page", defaultValue = "0") int page,
             @RequestParam(value = "size", defaultValue = "10") int size) {
         Pageable pageable = Pageable.ofSize(size).withPage(page);
         return  ResponseEntity.ok(
                 new ApiResponse<>(200, "Success", pageService.getAllPage(pageable)));
     }

     @Operation(summary = "Get page by id")
     @GetMapping("/{id}")
     public ResponseEntity<?> getPageById(@RequestParam Long id) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", pageService.getPageById(id)));
     }


}
