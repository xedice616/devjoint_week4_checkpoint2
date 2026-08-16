package com.devjoint.librarymanagementsystem.service.impl;

import com.devjoint.librarymanagementsystem.dto.request.BookRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.BookResponseDto;
import com.devjoint.librarymanagementsystem.entity.Author;
import com.devjoint.librarymanagementsystem.entity.Book;
import com.devjoint.librarymanagementsystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagementsystem.mapper.BookMapper;
import com.devjoint.librarymanagementsystem.repository.AuthorRepository;
import com.devjoint.librarymanagementsystem.repository.BookRepository;
import com.devjoint.librarymanagementsystem.service.BookService;
import com.devjoint.librarymanagementsystem.specification.BookSpecification;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;


    @Transactional
    @Override
    public BookResponseDto createBook(BookRequestDto requestDto) {

        Author author = authorRepository.findById(requestDto.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Author not found with id: "
                                        + requestDto.getAuthorId()));

        Book book = bookMapper.toEntity(requestDto);

        book.setAuthor(author);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toResponse(savedBook);
    }


    @Cacheable("books")
    @Override
    public BookResponseDto getBookById(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + id));

        return bookMapper.toResponse(book);
    }


    @Override
    public Page<BookResponseDto> getAllBooks(
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        return bookRepository
                .findAll(pageable)
                .map(bookMapper::toResponse);
    }


    @Transactional
    @Override
    public BookResponseDto updateBook(
            Long id,
            BookRequestDto requestDto) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + id));

        Author author = authorRepository.findById(
                        requestDto.getAuthorId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Author not found with id: "
                                        + requestDto.getAuthorId()));

        book.setTitle(requestDto.getTitle());
        book.setIsbn(requestDto.getIsbn());
        book.setPublicationYear(requestDto.getPublicationYear());
        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);

        return bookMapper.toResponse(updatedBook);
    }


    @Transactional
    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + id));

        bookRepository.delete(book);
    }


    @Override
    public Page<BookResponseDto> searchBooksByTitle(
            String title,
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        return bookRepository
                .findByTitleContainingIgnoreCase(
                        title,
                        pageable
                )
                .map(bookMapper::toResponse);
    }


    @Override
    public List<BookResponseDto> getAvailableBooks() {

        return bookRepository
                .findByAvailableTrue()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }


    @Override
    public List<BookResponseDto> getBooksPublishedAfter(
            Integer year) {

        return bookRepository
                .findBooksPublishedAfter(year)
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }


    @Override
    public List<BookResponseDto> getBooksByPublicationYear(
            Integer year) {

        return bookRepository
                .findBooksByPublicationYear(year)
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }


    @Override
    public Page<BookResponseDto> filterBooks(
            String title,
            Integer year,
            Boolean available,
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Specification<Book> specification = Specification.allOf(
                BookSpecification.hasTitle(title),
                BookSpecification.hasPublicationYear(year),
                BookSpecification.isAvailable(available)
        );

        return bookRepository
                .findAll(specification, pageable)
                .map(bookMapper::toResponse);
    }


    @Override
    public List<BookResponseDto> getAllBooksWithAuthorAndLoans() {

        return bookRepository
                .findAllWithAuthorAndLoans()
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }
}