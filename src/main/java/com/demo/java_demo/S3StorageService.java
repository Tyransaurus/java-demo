package com.demo.java_demo;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.internal.retry.SdkDefaultRetrySetting.Standard;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.charset.StandardCharsets;

@Service
public class S3StorageService 
{
    private final S3Client s3;

    public S3StorageService(S3Client s3) {
        this.s3 = s3;
    }


    public String getText(String bucketName, String key)
    {
        var req = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        return s3.getObjectAsBytes(req).asString(StandardCharsets.UTF_8);
    }

    public void putText(String bucket, String key, String text) {
        var req = PutObjectRequest.builder()
                .bucket(bucket).key(key)
                .contentType("text/plain; charset=utf-8")
                .build();
        s3.putObject(req, RequestBody.fromString(text, StandardCharsets.UTF_8));
    }
}
