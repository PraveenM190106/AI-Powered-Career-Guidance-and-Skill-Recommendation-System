# CareerAI - AI-Powered Career Guidance and Skill Recommendation System

## Overview

CareerAI is a console-based Java application that helps users manage their skills and certifications while receiving AI-powered career guidance using Gemini AI.

The system provides career recommendations, skill gap analysis, learning roadmaps, project suggestions, and technical question answering capabilities.

---

## Features

### User Management

* User Registration
* User Login Authentication

### Skill Management

* Add Skills
* View Skills
* Delete Skills
* Prevent Duplicate Skills

### Certification Management

* Add Certifications
* View Certifications
* Delete Certifications
* Prevent Duplicate Certifications

### AI Features

* Ask Technical Questions
* Career Prediction
* Skill Gap Analysis
* Learning Roadmap Generation
* Project Recommendations

### Report Generation

* Generate Complete Career Profile Report

---

## Technology Stack

### Backend

* Java 21
* JDBC

### Database

* MySQL

### AI Integration

* Google Gemini API

### Libraries

* MySQL Connector/J
* org.json

### IDE

* IntelliJ IDEA

---

## Database Schema

### users

| Column   | Type         |
| -------- | ------------ |
| id       | INT          |
| name     | VARCHAR(100) |
| email    | VARCHAR(100) |
| password | VARCHAR(100) |

### skills

| Column     | Type         |
| ---------- | ------------ |
| id         | INT          |
| user_id    | INT          |
| skill_name | VARCHAR(100) |

### certificates

| Column           | Type         |
| ---------------- | ------------ |
| id               | INT          |
| user_id          | INT          |
| certificate_name | VARCHAR(200) |

---

## Project Structure

src

├── ai

│ └── GeminiService.java

├── database

│ └── DBConnection.java

├── service

│ ├── UserService.java

│ ├── SkillService.java

│ ├── CertificateService.java

│ ├── CareerService.java

│ ├── RecommendationService.java

│ └── ReportService.java

└── Main.java

---

## Application Flow

1. Register User
2. Login User
3. Manage Skills
4. Manage Certifications
5. Ask AI Questions
6. Predict Career Paths
7. Perform Skill Gap Analysis
8. Generate Learning Roadmap
9. Recommend Projects
10. Generate Career Report

---

## Sample AI Query

Question:

How can I become a VLSI Engineer?

AI Response:

* Learn Digital Electronics
* Learn Verilog HDL
* Learn CMOS Design
* Learn Physical Design
* Build FPGA Projects
* Apply for VLSI Roles

---

## Future Enhancements

* Resume Analysis
* Job Recommendation System
* Web Application Version
* Spring Boot Integration
* User Dashboard
* PDF Report Generation

---

## Author

## PRAVEEN.M

BE Electronics and Communication Engineering (ECE)

