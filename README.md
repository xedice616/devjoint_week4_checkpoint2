# Library Management System – Week 3 Checkpoint

## Project Overview

During Week 3, the Library Management System was enhanced by implementing advanced Spring Boot features focused on performance, flexibility, transaction management, and testing. The project now includes dynamic filtering, optimized database queries, transaction rollback support, and expanded unit test coverage.

---

# Features Implemented

## 1. Dynamic Filtering with JPA Specifications

Implemented dynamic filtering using Spring Data JPA Specifications.

Books can now be filtered by:

- Title
- Publication Year
- Availability

Multiple filters can be combined within a single request without creating additional repository methods.

---

## 2. Pagination and Sorting

Pagination and sorting were implemented for service endpoints.

Supported parameters:

- page
- size
- sortBy
- sortDirection

Example:

```http
GET /books?page=0&size=10&sortBy=title&sortDirection=asc
```

---

## 3. EntityGraph Optimization

Implemented EntityGraph to optimize fetching related entities and reduce unnecessary SQL queries.

Example:

```java
@EntityGraph(attributePaths = {"author", "loans"})
List<Book> findAll();
```

This approach helps prevent the N+1 query problem by loading associated entities in a single query.

---

## 4. Transaction Management

Implemented transaction management using Spring's `@Transactional` annotation.

Loan creation is now executed inside a transaction to ensure database consistency.

Transaction flow:

- Validate book existence
- Validate member existence
- Check book availability
- Update book availability
- Save loan

If any operation fails, the transaction is rolled back automatically.

---

## 5. Rollback Scenario

A rollback scenario was implemented and tested.

If a user attempts to borrow a book that is already unavailable:

- An `IllegalStateException` is thrown.
- The loan is not saved.
- The book availability remains unchanged.

This guarantees data consistency.

---

## 6. Unit Testing

Service layer tests were implemented using JUnit 5 and Mockito.

Test coverage includes:

### AuthorService

- Create Author
- Get Author
- Update Author
- Delete Author

### BookService

- Create Book
- Get Book
- Update Book
- Delete Book

### MemberService

- Create Member
- Get Member
- Update Member
- Delete Member

### LoanService

- Create Loan
- Get Loan
- Update Loan
- Delete Loan
- Transaction Rollback Scenario

---

## 7. Repository Improvements

Implemented different query types.

### Derived Query Methods

```java
findByTitleContainingIgnoreCase()
findByAvailableTrue()
findByPublicationYearGreaterThanEqual()
```

### JPQL Queries

```java
@Query(...)
```

### Native SQL Queries

```java
@Query(nativeQuery = true)
```

---

## 8. Performance Improvements

Performance was improved by applying:

- EntityGraph
- Lazy Loading
- Pagination
- Sorting
- JPA Specifications

These optimizations reduce unnecessary database access and improve application efficiency.

---

# Technologies Used

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Spring Transaction Management
- JPA Specifications
- EntityGraph
- Lombok
- MapStruct
- JUnit 5
- Mockito
- Maven

---

# Project Architecture

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

---

# Testing

The project was tested using:

- Swagger UI
- JUnit 5
- Mockito

CRUD operations, filtering, pagination, sorting, transaction management, and rollback scenarios were verified successfully.

---

# Week 3 Summary

During this checkpoint, the project was extended with dynamic filtering using JPA Specifications, pagination and sorting, EntityGraph optimization, transaction management with rollback support, additional repository query methods, and comprehensive service layer unit tests.

These improvements make the application more scalable, maintainable, and closer to production-level Spring Boot development.

---
#Author
Khadijaaa Ahmadova


---
