package com.devjoint.librarymanagementsystem.repository;

import com.devjoint.librarymanagementsystem.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {


    // Derived Query Methods

    Page<Book> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    List<Book> findByAvailableTrue();

    List<Book> findByPublicationYearGreaterThanEqual(
            Integer year
    );


    // JPQL Query

    @Query("""
            SELECT b
            FROM Book b
            WHERE b.publicationYear >= :year
            """)
    List<Book> findBooksPublishedAfter(
            @Param("year") Integer year
    );


    // Native SQL Query

    @Query(
            value = """
                    SELECT *
                    FROM books
                    WHERE publication_year = :year
                    """,
            nativeQuery = true
    )
    List<Book> findBooksByPublicationYear(
            @Param("year") Integer year
    );


    // EntityGraph

    @EntityGraph(
            attributePaths = {"author", "loans"}
    )
    List<Book> findAllWithAuthorAndLoans();
}