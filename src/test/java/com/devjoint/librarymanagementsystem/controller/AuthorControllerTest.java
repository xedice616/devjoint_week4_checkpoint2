package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.AuthorRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.AuthorResponseDto;
import com.devjoint.librarymanagementsystem.service.AuthorService;
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

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createAuthor_shouldReturn201() throws Exception {

        AuthorRequestDto request = AuthorRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .build();

        AuthorResponseDto response = AuthorResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .build();

        when(authorService.createAuthor(any())).thenReturn(response);

        mockMvc.perform(post("/api/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void getAuthorById_shouldReturn200() throws Exception {

        AuthorResponseDto response = AuthorResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .build();

        when(authorService.getAuthorById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/authors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void updateAuthor_shouldReturn200() throws Exception {

        AuthorRequestDto request = AuthorRequestDto.builder()
                .firstName("Updated")
                .lastName("Author")
                .email("updated@test.com")
                .build();

        AuthorResponseDto response = AuthorResponseDto.builder()
                .id(1L)
                .firstName("Updated")
                .lastName("Author")
                .email("updated@test.com")
                .build();

        when(authorService.updateAuthor(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void deleteAuthor_shouldReturn204() throws Exception {

        doNothing().when(authorService).deleteAuthor(1L);

        mockMvc.perform(delete("/api/authors/1"))
                .andExpect(status().isNoContent());
    }

}