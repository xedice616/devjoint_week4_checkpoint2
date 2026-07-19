package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.MemberRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.MemberResponseDto;
import com.devjoint.librarymanagementsystem.service.MemberService;
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

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMember_shouldReturn201() throws Exception {

        MemberRequestDto request = MemberRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .phoneNumber("0501234567")
                .build();

        MemberResponseDto response = MemberResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .phoneNumber("0501234567")
                .build();

        when(memberService.createMember(any())).thenReturn(response);

        mockMvc.perform(post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void getMember_shouldReturn200() throws Exception {

        when(memberService.getMemberById(1L))
                .thenReturn(MemberResponseDto.builder().id(1L).build());

        mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateMember_shouldReturn200() throws Exception {

        MemberRequestDto request = MemberRequestDto.builder()
                .firstName("Updated")
                .lastName("User")
                .email("updated@test.com")
                .phoneNumber("0551112233")
                .build();

        when(memberService.updateMember(eq(1L), any()))
                .thenReturn(MemberResponseDto.builder().id(1L).build());

        mockMvc.perform(put("/api/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMember_shouldReturn204() throws Exception {

        mockMvc.perform(delete("/api/members/1"))
                .andExpect(status().isNoContent());
    }
}