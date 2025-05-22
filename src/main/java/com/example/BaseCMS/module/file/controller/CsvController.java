package com.example.BaseCMS.module.file.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.file.service.CsvService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/cvs")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService CsvService;
    @Operation(summary = "Export CSV")
    @GetMapping("export/product")
    public void exportProduct(HttpServletResponse response) throws Exception {
        CsvService.exportProductsCsv(response);
    }

    @Operation(summary = "Import CSV")
    @PostMapping(value ="import/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> importProduct( @RequestPart("file") MultipartFile file) throws Exception {
        CsvService.importProductCsv(file);
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", null));
    }
}
