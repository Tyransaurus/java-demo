package com.demo.java_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Controller public class S3Controller {

    private final S3StorageService s3;

    @Value("${app.s3.bucket}") private String bucket;

    public S3Controller(S3StorageService s3) {
        this.s3 = s3;
    }

    @GetMapping("/s3") public String view(Model model) {
        model.addAttribute("page", "s3");
        model.addAttribute("objects", s3.listObjects(bucket));
        return "s3";
    }

    @PostMapping("/s3/upload") public String upload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("message", "Please choose a file.");
                model.addAttribute("objects", s3.listObjects(bucket));
                return "s3";
            }

            String key = file.getOriginalFilename();
            String contentType = file.getContentType();
            s3.uploadBytes(bucket, key, file.getBytes(),
                (contentType != null ? contentType : "application/octet-stream"));
            model.addAttribute("message", "Uploaded: " + key);
        } catch (Exception e) {
            model.addAttribute("message", "Upload failed: " + e.getMessage());
        }

        // refresh list after upload
        model.addAttribute("objects", s3.listObjects(bucket));
        return "s3";
    }

    @GetMapping("/s3/download") public ResponseEntity < byte[] > download(@RequestParam("key") String key) {
        byte[] data = s3.getBytes(bucket, key);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + key + "\"")
            .contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
    }
}