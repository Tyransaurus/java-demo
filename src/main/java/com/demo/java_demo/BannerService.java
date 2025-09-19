package com.demo.java_demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BannerService {
    private final S3StorageService s3;

    @Value("${app.s3.bucket}")
    private String bucket;

    @Value("${app.s3.bannerKey}")
    private String bannerKey;

    public BannerService(S3StorageService s3) {
        this.s3 = s3;
    }

    public String readBanner() {
        try {
            return s3.getText(bucket, bannerKey);
        } catch (Exception e) {
            return "(no banner found in S3)";
        }
    }

    public void writeBanner(String text) {
        s3.putText(bucket, bannerKey, text);
    }

    public void resetBanner() {
        String original = s3.getText(bucket, bannerKey.replace(".txt", "-original.txt"));
        s3.putText(bucket, bannerKey, original);
    }

}