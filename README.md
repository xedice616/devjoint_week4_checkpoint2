# Library Management System

A Spring Boot based REST API for managing library resources, including books, authors, members and loans.

The project follows a layered architecture and demonstrates common backend development practices such as RESTful API design, Spring Data JPA, validation, pagination, caching, file handling and centralized exception management.

---

## Tech Stack

- Java 17
- Spring Boot 3.5.3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- PostgreSQL
- Hibernate
- MapStruct
- Lombok
- Spring Cache
- SpringDoc OpenAPI / Swagger
- Maven
- JUnit 5
- Mockito

---

# Checkpoint 2 — File Upload & Download

## Overview

This checkpoint introduces file upload and download functionality to the Library Management System.

The implementation supports multipart file uploads, server-side file storage, file type validation and file size validation.

Supported file types:

- JPEG / JPG
- PNG
- PDF

Maximum file size:

- 5 MB

---

## Architecture

The file handling functionality follows the service-oriented layered architecture:

```text
Client / Postman
       |
       v
FileController
       |
       v
FileStorageService
       |
       v
FileStorageServiceImpl
       |
       v
Local File System
       |
       v
uploads/
