package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.LoanRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.LoanResponseDto;
import com.devjoint.librarymanagementsystem.service.LoanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoanService loanService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createLoan_shouldReturn201() throws Exception {

        LoanRequestDto request = LoanRequestDto.builder()
                .bookId(1L)
                .memberId(1L)
                .build();

        when(loanService.createLoan(any()))
                .thenReturn(LoanResponseDto.builder().id(1L).build());

        mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void getLoan_shouldReturn200() throws Exception {

        when(loanService.getLoanById(1L))
                .thenReturn(LoanResponseDto.builder().id(1L).build());

        mockMvc.perform(get("/api/loans/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateLoan_shouldReturn200() throws Exception {

        LoanRequestDto request = LoanRequestDto.builder()
                .bookId(1L)
                .memberId(1L)
                .build();

        when(loanService.updateLoan(eq(1L), any()))
                .thenReturn(LoanResponseDto.builder().id(1L).build());

        mockMvc.perform(put("/api/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteLoan_shouldReturn204() throws Exception {

        mockMvc.perform(delete("/api/loans/1"))
                .andExpect(status().isNoContent());
    }
}