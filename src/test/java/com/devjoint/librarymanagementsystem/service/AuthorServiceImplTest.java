package com.devjoint.librarymanagementsystem.service;

import com.devjoint.librarymanagementsystem.dto.request.AuthorRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.AuthorResponseDto;
import com.devjoint.librarymanagementsystem.entity.Author;
import com.devjoint.librarymanagementsystem.mapper.AuthorMapper;
import com.devjoint.librarymanagementsystem.repository.AuthorRepository;
import com.devjoint.librarymanagementsystem.service.impl.AuthorServiceImpl;
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
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    void createAuthor_success() {

        AuthorRequestDto request = AuthorRequestDto.builder()
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        Author author = Author.builder()
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        Author savedAuthor = Author.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        AuthorResponseDto response = AuthorResponseDto.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        when(authorMapper.toEntity(request)).thenReturn(author);
        when(authorRepository.save(author)).thenReturn(savedAuthor);
        when(authorMapper.toResponse(savedAuthor)).thenReturn(response);

        AuthorResponseDto result = authorService.createAuthor(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFirstName()).isEqualTo("Robert");

        verify(authorMapper).toEntity(request);
        verify(authorRepository).save(author);
        verify(authorMapper).toResponse(savedAuthor);
    }

    @Test
    void getAuthorById_success() {

        Author author = Author.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        AuthorResponseDto response = AuthorResponseDto.builder()
                .id(1L)
                .firstName("Robert")
                .lastName("Martin")
                .email("robert@gmail.com")
                .build();

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.toResponse(author)).thenReturn(response);

        AuthorResponseDto result = authorService.getAuthorById(1L);

        assertThat(result.getId()).isEqualTo(1L);

        verify(authorRepository).findById(1L);
        verify(authorMapper).toResponse(author);
    }

    @Test
    void getAuthorById_notFound() {

        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authorService.getAuthorById(1L));

        assertThat(exception.getMessage())
                .isEqualTo("Author not found with id: 1");

        verify(authorRepository).findById(1L);
    }

    @Test
    void deleteAuthor_success() {

        Author author = Author.builder()
                .id(1L)
                .build();

        when(authorRepository.findById(1L))
                .thenReturn(Optional.of(author));

        authorService.deleteAuthor(1L);

        verify(authorRepository).delete(author);
    }

    @Test
    void deleteAuthor_notFound() {

        when(authorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> authorService.deleteAuthor(1L));

        verify(authorRepository, never()).delete(any());
    }
}