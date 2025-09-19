package com.demo.java_demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

    @Bean
    public S3Client s3Client() {
        // Load .env if it exists (won’t crash if it doesn’t)
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()   // allows running without a .env
                .load();

        String accessKey = getenv(dotenv, "AWS_ACCESS_KEY_ID");
        String secretKey = getenv(dotenv, "AWS_SECRET_ACCESS_KEY");
        String regionStr = getenv(dotenv, "AWS_REGION", "us-east-2");

        AwsCredentialsProvider provider;
        if (notBlank(accessKey) && notBlank(secretKey)) {
            provider = StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey)
            );
        } else {
            // fallback to AWS default provider chain (env, profile, role, etc.)
            provider = DefaultCredentialsProvider.create();
        }

        return S3Client.builder()
                .region(Region.of(regionStr))
                .credentialsProvider(provider)
                .build();
    }

    private static String getenv(Dotenv d, String key) {
        // .env value first, else OS env
        String v = d.get(key);
        if (v == null || v.isBlank()) v = System.getenv(key);
        return v;
    }

    private static String getenv(Dotenv d, String key, String def) {
        String v = getenv(d, key);
        return (v == null || v.isBlank()) ? def : v;
    }

    private static boolean notBlank(String s) { return s != null && !s.isBlank(); }
}