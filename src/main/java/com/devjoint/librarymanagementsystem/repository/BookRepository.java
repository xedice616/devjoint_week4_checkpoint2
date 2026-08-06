package com.devjoint.librarymanagementsystem.repository;

import com.devjoint.librarymanagementsystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;

public interface BookRepository extends JpaRepository<Book, Long>,
        JpaSpecificationExecutor<Book> {
    // Derived Query Methods
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAvailableTrue();

    List<Book> findByPublicationYearGreaterThanEqual(Integer year);

    // JPQL Query
    @Query("""
            SELECT b
            FROM Book b
            WHERE b.publicationYear >= :year
            """)
    List<Book> findBooksPublishedAfter(@Param("year") Integer year);

    // Native SQL Query
    @Query(
            value = """
                    SELECT *
                    FROM books
                    WHERE publication_year = :year
                    """,
            nativeQuery = true
    )
    List<Book> findBooksByPublicationYear(@Param("year") Integer year);




    @EntityGraph(attributePaths = {"author", "loans"})
    @Query("SELECT b FROM Book b")
    List<Book> findAllWithAuthorAndLoans();
    //elave sorgu sayi azalir bunun sayesinde ve  nperformans daha da yaxsilasdirirlir
}