package com.devjoint.librarymanagementsystem.service;
import com.devjoint.librarymanagementsystem.dto.request.BookRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import java.util.List;

public interface BookService {

    BookResponseDto createBook(BookRequestDto requestDto);

    BookResponseDto getBookById(Long id);

    Page<BookResponseDto> getAllBooks(
            int page,
            int size,
            String sortBy,
            String sortDirection);

    List<BookResponseDto> filterBooks(
            String title,
            Integer year,
            Boolean available);



    BookResponseDto updateBook(Long id, BookRequestDto requestDto);
    void deleteBook(Long id);

    List<BookResponseDto> searchBooksByTitle(String title);

    List<BookResponseDto> getAvailableBooks();

    List<BookResponseDto> getBooksPublishedAfter(Integer year);


    List<BookResponseDto> getAllBooksWithAuthorAndLoans();

    List<BookResponseDto> getBooksByPublicationYear(Integer year);
}