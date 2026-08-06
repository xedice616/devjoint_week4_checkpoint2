# Library Management System

A RESTful Library Management System built with **Spring Boot** following a layered architecture. 
The project provides complete CRUD operations 
for managing Authors, Books, Members, and Loans.

---

## Technologies

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- Jakarta Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito
- MockMvc

---

## Features

- RESTful CRUD APIs
- Layered Architecture (Controller → Service → Repository)
- DTO Mapping using MapStruct
- Input Validation
- Global Exception Handling
- Pagination & Sorting
- Swagger/OpenAPI Documentation
- Unit Testing
- Controller Testing

---

## Validation

The project uses Jakarta Validation annotations including:

- `@NotBlank`
- `@NotNull`
- `@Email`
- `@Size`
- `@Min`

---
## Checkpoint 6 - Transaction Rollback Test

A rollback scenario test was added for the loan transaction.

- Tested transaction behavior when a borrowed book is requested.
- Verified that an exception is thrown.
- Verified that neither Book nor Loan is saved when the transaction fails.

---


## Exception Handling

Centralized exception handling is implemented using:

- `@ControllerAdvice`
- `@ExceptionHandler`
- `ResourceNotFoundException`

---

## Pagination & Sorting

Example request:

```http
GET /api/authors?page=0&size=5&sortBy=id&sortDirection=asc
```

---

## API Documentation

Swagger UI is available after starting the application.

```
http://localhost:8080/swagger-ui/index.html
```

The documentation includes:

- Author API
- Book API
- Member API
- Loan API
- Request & Response Schemas
- HTTP Status Codes

---

## Testing

The project includes both **Service Layer Unit Tests** and **Controller Layer Tests**.

### Service Tests

- AuthorServiceImplTest
- BookServiceImplTest
- MemberServiceImplTest
- LoanServiceImplTest

### Controller Tests

- AuthorControllerTest
- BookControllerTest
- MemberControllerTest
- LoanControllerTest

Testing technologies:

- JUnit 5
- Mockito
- MockMvc

---

## Run the Project

1. Clone the repository

```bash
git clone <https://github.com/xedice616/devjoint_week1_checkpoint7>
```

2. Configure PostgreSQL.

3. Update database credentials in:

```yaml
application.yaml
```

4. Start the application.

5. Open Swagger UI.

---


## Environment Variables

This project uses environment variables for sensitive configuration.

Required:

- DB_PASSWORD

Example:

password: ${DB_PASSWORD}

## Author

**Khadija Ahmadova**
ahmedova_k
