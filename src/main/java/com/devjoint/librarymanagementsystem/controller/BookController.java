package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.BookRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.BookResponseDto;
import com.devjoint.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDto createBook(
            @Valid @RequestBody BookRequestDto requestDto) {

        return bookService.createBook(requestDto);
    }

    @GetMapping("/{id}")
    public BookResponseDto getBookById(
            @PathVariable Long id) {

        return bookService.getBookById(id);
    }

    @GetMapping
    public Page<BookResponseDto> getAllBooks(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDirection) {

        return bookService.getAllBooks(
                page,
                size,
                sortBy,
                sortDirection
        );
    }

    @PutMapping("/{id}")
    public BookResponseDto updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequestDto requestDto) {

        return bookService.updateBook(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(
            @PathVariable Long id) {

        bookService.deleteBook(id);
    }


    @GetMapping("/search")
    public List<BookResponseDto> searchBooksByTitle(
            @RequestParam String title) {

        return bookService.searchBooksByTitle(title);
    }


    @GetMapping("/available")
    public List<BookResponseDto> getAvailableBooks() {

        return bookService.getAvailableBooks();
    }

    @GetMapping("/published-after/{year}")
    public List<BookResponseDto> getBooksPublishedAfter(
            @PathVariable Integer year) {

        return bookService.getBooksPublishedAfter(year);
    }



    @GetMapping("/publication-year/{year}")
    public List<BookResponseDto> getBooksByPublicationYear(
            @PathVariable Integer year) {

        return bookService.getBooksByPublicationYear(year);
    }



}