package com.devjoint.librarymanagementsystem.specification;

import com.devjoint.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->

                title == null || title.isBlank()
                        ? null
                        : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

    public static Specification<Book> hasPublicationYear(Integer year) {
        return (root, query, criteriaBuilder) ->

                year == null
                        ? null
                        : criteriaBuilder.equal(
                        root.get("publicationYear"),
                        year
                );
    }

    public static Specification<Book> isAvailable(Boolean available) {
        return (root, query, criteriaBuilder) ->

                available == null
                        ? null
                        : criteriaBuilder.equal(
                        root.get("available"),
                        available
                );
    }
}

/*
Bu class 3 ayrı filtr yaradır:
 */