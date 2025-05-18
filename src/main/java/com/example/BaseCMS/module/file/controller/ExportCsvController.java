package com.example.BaseCMS.module.file.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.file.service.ExportCsvService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/export")
@RequiredArgsConstructor
public class ExportCsvController {

    private final ExportCsvService exportCsvService;
    @Operation(summary = "Export CSV")
    @GetMapping("/product")
    public ResponseEntity<?> exportProduct(HttpServletResponse response) throws Exception {
        exportCsvService.exportProductsCsv(response);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }
}
