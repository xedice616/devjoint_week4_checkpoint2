package com.devjoint.librarymanagementsystem.service.impl;

import com.devjoint.librarymanagementsystem.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String UPLOAD_DIR = "uploads";

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "application/pdf"
    );

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "File size must not exceed 5 MB."
            );
        }

        String contentType = file.getContentType();

        if (!ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Unsupported file type. Allowed types: JPG, PNG, PDF."
            );
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException(
                    "File name must not be empty."
            );
        }

        String fileName = System.currentTimeMillis()
                + "_"
                + Paths.get(originalFileName)
                .getFileName()
                .toString();

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                file.getInputStream(),
                filePath
        );

        return fileName;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {

        Path filePath = Paths.get(UPLOAD_DIR)
                .resolve(fileName)
                .normalize();

        if (!Files.exists(filePath)) {
            throw new IllegalArgumentException(
                    "File not found: " + fileName
            );
        }

        return Files.readAllBytes(filePath);
    }
}