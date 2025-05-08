package com.example.BaseCMS.module.page.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.page.dto.PageDto;
import com.example.BaseCMS.module.page.model.Page;
import com.example.BaseCMS.module.page.rq.PageRequest;
import com.example.BaseCMS.module.page.service.PageService;
import com.example.BaseCMS.module.user.service.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/pages")
@RequiredArgsConstructor
public class AdminPageController {
    private final PageService pageService;

    @Operation(summary = "Create page")
    @PostMapping("/")
    public ResponseEntity<?> createPage(@RequestBody()PageRequest pageRq) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        Long userId = userDetails.getId();
        Page page =  pageService.createPage(pageRq, userId);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", page));
    }

    @Operation(summary = "List pages")
    @GetMapping("/")
    public ResponseEntity<?> listPages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", pageService.getAllPage(pageable)));
    }

    @Operation(summary = "Get page by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPageById(@PathVariable Long id) {
        PageDto page = pageService.getPageById(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", page));
    }

    @Operation(summary = "Update page")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updatePage(@PathVariable Long id, @RequestBody PageRequest pageRq) {
        pageService.updatePage(id, pageRq);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }

}
