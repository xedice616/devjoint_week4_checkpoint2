package com.devjoint.librarymanagementsystem.service;

import com.devjoint.librarymanagementsystem.dto.request.MemberRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.MemberResponseDto;
import com.devjoint.librarymanagementsystem.entity.Member;
import com.devjoint.librarymanagementsystem.mapper.MemberMapper;
import com.devjoint.librarymanagementsystem.repository.MemberRepository;
import com.devjoint.librarymanagementsystem.service.impl.MemberServiceImpl;
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
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void createMember_success() {

        MemberRequestDto request = MemberRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .phoneNumber("0501234567")
                .build();

        Member member = new Member();
        Member savedMember = new Member();
        savedMember.setId(1L);

        MemberResponseDto response = MemberResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@test.com")
                .phoneNumber("0501234567")
                .build();

        when(memberMapper.toEntity(request)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(savedMember);
        when(memberMapper.toResponse(savedMember)).thenReturn(response);

        MemberResponseDto result = memberService.createMember(request);

        assertThat(result.getId()).isEqualTo(1L);

        verify(memberRepository).save(member);
    }

    @Test
    void getMemberById_success() {

        Member member = new Member();
        member.setId(1L);

        MemberResponseDto response = MemberResponseDto.builder()
                .id(1L)
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(memberMapper.toResponse(member))
                .thenReturn(response);

        MemberResponseDto result = memberService.getMemberById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getMemberById_notFound() {

        when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> memberService.getMemberById(1L));
    }

    @Test
    void deleteMember_success() {

        Member member = new Member();
        member.setId(1L);

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        memberService.deleteMember(1L);

        verify(memberRepository).delete(member);
    }

    @Test
    void updateMember_success() {

        MemberRequestDto request = MemberRequestDto.builder()
                .firstName("New")
                .lastName("User")
                .email("new@test.com")
                .phoneNumber("0551112233")
                .build();

        Member member = new Member();
        member.setId(1L);

        MemberResponseDto response = MemberResponseDto.builder()
                .id(1L)
                .firstName("New")
                .lastName("User")
                .email("new@test.com")
                .phoneNumber("0551112233")
                .build();

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(memberRepository.save(member))
                .thenReturn(member);

        when(memberMapper.toResponse(member))
                .thenReturn(response);

        MemberResponseDto result =
                memberService.updateMember(1L, request);

        assertThat(result.getFirstName()).isEqualTo("New");
    }
}