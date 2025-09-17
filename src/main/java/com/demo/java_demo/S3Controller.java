package com.demo.java_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class S3Controller {

    private final S3StorageService s3;

    @Value("${app.s3.bucket}")
    private String bucket;

    public S3Controller(S3StorageService s3) {
        this.s3 = s3;
    }

    @GetMapping("/s3")
    public String view() {
        return "s3";
    }

    @PostMapping("/s3/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        try {
            if (file.isEmpty()) {
                model.addAttribute("message", "Please choose a file.");
                return "s3";
            }
            String key = file.getOriginalFilename();
            String contentType = file.getContentType();
            s3.uploadBytes(bucket, key, file.getBytes(), contentType != null ? contentType : "application/octet-stream");
            model.addAttribute("message", "Uploaded: " + key);
        } catch (Exception e) {
            model.addAttribute("message", "Upload failed: " + e.getMessage());
        }
        return "s3";
    }
}