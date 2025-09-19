package com.demo.java_demo;

import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
public class ETLController {

    private final S3StorageService s3;

    @Value("${app.s3.bucket}")
    private String bucket;

    public ETLController(S3StorageService s3) {
        this.s3 = s3;
    }

    // Step 1: choose a CSV in S3
    @GetMapping("/etl")
    public String etlHome(Model model) {
        model.addAttribute("page", "etl");
        model.addAttribute("csvKeys", s3.listObjects(bucket).stream()
                .filter(k -> k.toLowerCase().endsWith(".csv")).toList());
        return "etl";
    }

    // Step 2: preview headers of selected CSV
    @PostMapping("/etl/preview")
    public String preview(@RequestParam("key") String key, Model model) {
        model.addAttribute("page", "etl");
        model.addAttribute("csvKeys", s3.listObjects(bucket).stream()
                .filter(k -> k.toLowerCase().endsWith(".csv")).toList());
        model.addAttribute("selectedKey", key);

        byte[] data = s3.getBytes(bucket, key);
        try (Reader r = new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8)) {
            CSVParser parser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader()
                    .setSkipHeaderRecord(false)
                    .build()
                    .parse(r);
            // If file has header row, header names are here:
            List<String> headers = new ArrayList<>(parser.getHeaderNames());
            // Fallback if no header row present: synthesize columns
            if (headers.isEmpty() && parser.iterator().hasNext()) {
                int width = parser.iterator().next().size();
                for (int i = 0; i < width; i++) headers.add("col" + (i + 1));
            }
            model.addAttribute("headers", headers);
        } catch (IOException e) {
            model.addAttribute("error", "Failed to read CSV headers: " + e.getMessage());
        }
        return "etl";
    }

    // Step 3: export only selected columns as a downloadable CSV (streamed)
    @PostMapping("/etl/export")
    public ResponseEntity<StreamingResponseBody> export(
            @RequestParam("key") String key,
            @RequestParam("cols") List<String> cols) {

        String filename = key.replaceAll("[^a-zA-Z0-9._-]", "_")
                .replace(".csv", "") + "_filtered.csv";

        StreamingResponseBody body = out -> {
            byte[] data = s3.getBytes(bucket, key);
            try (Reader r = new InputStreamReader(new ByteArrayInputStream(data), StandardCharsets.UTF_8);
                 CSVParser parser = CSVFormat.DEFAULT
                         .builder()
                         .setHeader()
                         .setSkipHeaderRecord(false)
                         .build()
                         .parse(r);
                 OutputStreamWriter w = new OutputStreamWriter(out, StandardCharsets.UTF_8);
                 CSVPrinter printer = new CSVPrinter(w, CSVFormat.DEFAULT)) {

                // Write selected header row
                printer.printRecord(cols);

                // If the file had headers, parser provides records with header mapping
                for (CSVRecord rec : parser) {
                    List<String> row = new ArrayList<>(cols.size());
                    for (String c : cols) {
                        // Safe lookup (missing header -> empty)
                        String val = rec.isMapped(c) ? rec.get(c) : "";
                        row.add(val);
                    }
                    printer.printRecord(row);
                }
            }
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(body);
    }
}