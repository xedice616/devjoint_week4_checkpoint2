package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file) {

        try {

            String fileName =
                    fileStorageService.uploadFile(file);

            return ResponseEntity.ok(
                    "File uploaded successfully: " + fileName
            );

        } catch (IOException e) {

            return ResponseEntity.internalServerError()
                    .body("File upload failed.");
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @PathVariable String fileName) {

        try {

            byte[] data =
                    fileStorageService.downloadFile(fileName);

            ByteArrayResource resource =
                    new ByteArrayResource(data);

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\""
                    )
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(data.length)
                    .body(resource);

        } catch (IOException e) {

            return ResponseEntity.notFound().build();
        }
    }
}