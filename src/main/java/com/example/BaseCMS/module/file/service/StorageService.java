package com.example.BaseCMS.module.file.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {

    private final Path root = Paths.get("uploads");

    public StorageService() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public String uploadImageToFileSystem(MultipartFile file) {
        try {

            String originalFilename = file.getOriginalFilename();
            String filename = System.currentTimeMillis() + "_" + Paths.get(originalFilename).getFileName().toString();
            Path destinationFile = this.root.resolve(filename).normalize().toAbsolutePath();


            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Path downloadImageFromFileSystem(String filename) {
        try {
            Path filePath = this.root.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return filePath;
            } else {
                throw new RuntimeException("File not found or not readable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading file. Error: " + e.getMessage());
        }
    }

}
