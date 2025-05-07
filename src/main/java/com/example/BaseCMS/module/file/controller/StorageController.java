package com.example.BaseCMS.module.file.controller;

import com.example.BaseCMS.common.ApiResponse;
import com.example.BaseCMS.module.file.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/v1/file-storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;
    @Value("${base.url}")
    private String baseUrl;


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadImageToFileSystem(
            @RequestPart("file") MultipartFile file) {

        String fileId = storageService.uploadImageToFileSystem(file);

        String viewUrl = baseUrl + "/v1/file-storage/view/" + fileId;

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "File uploaded successfully",
                viewUrl
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<?> viewImageFromFileSystem(@PathVariable String id) {
        try {
            Path filePath = storageService.downloadImageFromFileSystem(id);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.status(404).body(new ApiResponse<>(404, "File not found", null));
            }

            String contentType = Files.probeContentType(filePath);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(500, "Error loading file", null));
        }
    }
}
