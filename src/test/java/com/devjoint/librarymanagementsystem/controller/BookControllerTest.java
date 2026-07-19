package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.BookRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.BookResponseDto;
import com.devjoint.librarymanagementsystem.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createBook_shouldReturn201() throws Exception {

        BookRequestDto request = BookRequestDto.builder()
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .authorId(1L)
                .build();

        BookResponseDto response = BookResponseDto.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .build();

        when(bookService.createBook(any())).thenReturn(response);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void getBookById_shouldReturn200() throws Exception {

        BookResponseDto response = BookResponseDto.builder()
                .id(1L)
                .title("Clean Code")
                .isbn("9780132350884")
                .publicationYear(2008)
                .build();

        when(bookService.getBookById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void updateBook_shouldReturn200() throws Exception {

        BookRequestDto request = BookRequestDto.builder()
                .title("Effective Java")
                .isbn("9780134685991")
                .publicationYear(2018)
                .authorId(1L)
                .build();

        BookResponseDto response = BookResponseDto.builder()
                .id(1L)
                .title("Effective Java")
                .isbn("9780134685991")
                .publicationYear(2018)
                .build();

        when(bookService.updateBook(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Effective Java"));
    }

    @Test
    void deleteBook_shouldReturn204() throws Exception {

        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}