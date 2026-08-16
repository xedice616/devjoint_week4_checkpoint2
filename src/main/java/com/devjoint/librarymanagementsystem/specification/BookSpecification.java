package com.devjoint.librarymanagementsystem.specification;

import com.devjoint.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {

        return (root, query, criteriaBuilder) -> {

            if (title == null || title.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasPublicationYear(Integer year) {

        return (root, query, criteriaBuilder) -> {

            if (year == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("publicationYear"),
                    year
            );
        };
    }

    public static Specification<Book> isAvailable(Boolean available) {

        return (root, query, criteriaBuilder) -> {

            if (available == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(
                    root.get("available"),
                    available
            );
        };
    }
}