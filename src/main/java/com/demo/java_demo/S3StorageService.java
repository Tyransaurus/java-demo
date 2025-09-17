package com.demo.java_demo;

import org.springframework.stereotype.Service;

import software.amazon.awssdk.core.internal.retry.SdkDefaultRetrySetting.Standard;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

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

    public void uploadBytes(String bucket, String key, byte[] data, String contentType) {
        var req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();
        s3.putObject(req, RequestBody.fromBytes(data));
    }

    public java.util.List<String> listObjects(String bucket) {
        var req = ListObjectsV2Request.builder()
            .bucket(bucket)
            .maxKeys(100)        // adjust as you like
            .build();
        var resp = s3.listObjectsV2(req);
        java.util.List<String> keys = new java.util.ArrayList<>();
        for (S3Object obj : resp.contents()) {
            keys.add(obj.key());
        }
        return keys;
    }

    // Optional: helper to download bytes 
    public byte[] getBytes(String bucket, String key) {
        var get = GetObjectRequest.builder().bucket(bucket).key(key).build();
        return s3.getObjectAsBytes(get).asByteArray();
    }
}
