# Java AWS Demo – Spring Boot Web App

A demo **Spring Boot web application** showcasing:
- Algorithmic challenges (Java & JS)
- AWS S3 file storage integration
- ETL demo (CSV upload → select columns → export filtered CSV)
- Unit testing with JUnit 5
- Modern web UI with Thymeleaf templates & fragments

---

## 🚀 Features

### 🔹 Algorithm Challenges
Interactive coding challenges with results displayed dynamically:
- **Two Sum** – find index pairs that add to a target
- **Reverse String**
- **Valid Parentheses**
- **Group Anagrams**
- **Longest Substring Without Repeating Characters**
- **Merge Intervals**
- **Palindrome (JS only)** – client-side validation with JavaScript

### 🔹 AWS S3 Integration
- Upload and store files in your configured S3 bucket
- List available objects
- Download files directly from S3
- Banner text stored in S3 for easy update/reset

### 🔹 ETL Demo
- Upload or select a CSV file from S3
- Preview headers
- Select columns to export
- Download filtered CSV instantly
- Built for demo with `fake_etl_demo.csv` (100 rows of fake employee data)

### 🔹 Testing
- JUnit 5 unit tests for `ChallengeService`
- Covers algorithms (string, arrays, hash maps, stacks, sliding window, intervals)
- Ready for CI/CD pipeline integration

---

## 🛠️ Tech Stack
- **Java 21**  
- **Spring Boot 3** (Web, Thymeleaf, Session, Config)  
- **AWS SDK v2 (S3)**  
- **Commons CSV** (ETL CSV parsing)  
- **Thymeleaf** (views & fragments)  
- **JUnit 5 + Mockito** (testing)  
- **GitHub Actions** (CI/CD ready)  

---

## ⚙️ Setup

### Prerequisites
- Java 21  
- Maven 3+  
- AWS account + S3 bucket (optional for ETL/S3 pages)  

### Environment Variables (`.env`)
```env
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
AWS_REGION=us-east-1
APP_S3_BUCKET=your-bucket-name