# Library Management System

A RESTful Library Management System built with Spring Boot.

## Technologies

- Java 17
- Spring Boot 3.5.3
- Spring Data JPA
- PostgreSQL
- Lombok
- MapStruct
- Swagger / OpenAPI

## Features

- CRUD APIs for Authors, Books, Members and Loans
- Layered Architecture (Controller → Service → Repository)
- DTO Mapping with MapStruct
- Input Validation
- Global Exception Handling
- Pagination
- Sorting
## Validation

The project uses Jakarta Validation annotations such as:

- @NotBlank
- @NotNull
- @Email
- @Size
- @Min


### Pagination Example

GET /api/authors?page=0&size=5&sortBy=id&sortDirection=asc


## Exception Handling

Implemented centralized exception handling using:

- @ControllerAdvice
- @ExceptionHandler
- ResourceNotFoundException

## API Documentation

Swagger UI is available after running the application.

Open the following URL in your browser:

http://localhost:8080/swagger-ui/index.html

The API documentation includes:

- Author endpoints
- Book endpoints
- Member endpoints
- Loan endpoints
- Request/Response schemas
- HTTP status codes
- 

## Run the Project

1. Clone the repository.
2. Configure PostgreSQL database.
3. Update the database credentials in `application.yaml`.
4. Run the application.
5. Open Swagger UI to test the endpoints.

## Author
ahmedova_k