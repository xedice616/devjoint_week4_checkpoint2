package com.devjoint.librarymanagementsystem.service;

import com.devjoint.librarymanagementsystem.dto.request.BookRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.BookResponseDto;
import com.devjoint.librarymanagementsystem.entity.Author;
import com.devjoint.librarymanagementsystem.entity.Book;
import com.devjoint.librarymanagementsystem.mapper.BookMapper;
import com.devjoint.librarymanagementsystem.repository.AuthorRepository;
import com.devjoint.librarymanagementsystem.repository.BookRepository;
import com.devjoint.librarymanagementsystem.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void createBook_success() {

        BookRequestDto request = BookRequestDto.builder()
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .authorId(1L)
                .build();

        Author author = Author.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Martin")
                .build();

        Book book = Book.builder()
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .build();

        Book savedBook = Book.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .author(author)
                .build();

        BookResponseDto response = BookResponseDto.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .authorFullName("Robert Martin")
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookMapper.toEntity(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(savedBook);
        when(bookMapper.toResponse(savedBook)).thenReturn(response);

        BookResponseDto result = bookService.createBook(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Clean Code");

        verify(authorRepository).findById(1L);
        verify(bookRepository).save(book);
    }

    @Test
    void getBookById_success() {

        Author author = Author.builder().id(1L).build();

        Book book = Book.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .author(author)
                .build();

        BookResponseDto response = BookResponseDto.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .authorFullName("Robert Martin")
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(response);

        BookResponseDto result = bookService.getBookById(1L);

        assertThat(result.getId()).isEqualTo(1L);

        verify(bookRepository).findById(1L);
        verify(bookMapper).toResponse(book);
    }

    @Test
    void getBookById_notFound() {

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> bookService.getBookById(1L)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Book not found with id: 1");
    }

    @Test
    void deleteBook_success() {

        Book book = Book.builder()
                .id(1L)
                .build();

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_notFound() {

        when(bookRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> bookService.deleteBook(1L));

        verify(bookRepository, never()).delete(any());
    }
}