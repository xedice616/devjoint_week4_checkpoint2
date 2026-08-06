package com.devjoint.librarymanagementsystem.service;

import com.devjoint.librarymanagementsystem.dto.request.LoanRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.LoanResponseDto;
import com.devjoint.librarymanagementsystem.entity.Book;
import com.devjoint.librarymanagementsystem.entity.Loan;
import com.devjoint.librarymanagementsystem.entity.Member;
import com.devjoint.librarymanagementsystem.mapper.LoanMapper;
import com.devjoint.librarymanagementsystem.repository.BookRepository;
import com.devjoint.librarymanagementsystem.repository.LoanRepository;
import com.devjoint.librarymanagementsystem.repository.MemberRepository;
import com.devjoint.librarymanagementsystem.service.impl.LoanServiceImpl;
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
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanServiceImpl loanService;

    @Test
    void createLoan_success() {

        LoanRequestDto request = LoanRequestDto.builder()
                .bookId(1L)
                .memberId(1L)
                .build();

        Book book = new Book();
        book.setId(1L);

        Member member = new Member();
        member.setId(1L);

        Loan loan = new Loan();

        Loan savedLoan = new Loan();
        savedLoan.setId(1L);

        LoanResponseDto response = LoanResponseDto.builder()
                .id(1L)
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(loanMapper.toEntity(request)).thenReturn(loan);
        when(loanRepository.save(loan)).thenReturn(savedLoan);
        when(loanMapper.toResponse(savedLoan)).thenReturn(response);

        LoanResponseDto result = loanService.createLoan(request);

        assertThat(result.getId()).isEqualTo(1L);

        verify(loanRepository).save(loan);
    }

    @Test
    void getLoanById_success() {

        Loan loan = new Loan();
        loan.setId(1L);

        LoanResponseDto response = LoanResponseDto.builder()
                .id(1L)
                .build();

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        when(loanMapper.toResponse(loan))
                .thenReturn(response);

        LoanResponseDto result = loanService.getLoanById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getLoanById_notFound() {

        when(loanRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> loanService.getLoanById(1L));
    }

    @Test
    void deleteLoan_success() {

        Loan loan = new Loan();
        loan.setId(1L);

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        loanService.deleteLoan(1L);

        verify(loanRepository).delete(loan);
    }

    @Test
    void updateLoan_success() {

        LoanRequestDto request = LoanRequestDto.builder()
                .bookId(1L)
                .memberId(1L)
                .build();

        Loan loan = new Loan();
        loan.setId(1L);

        Book book = new Book();
        book.setId(1L);

        Member member = new Member();
        member.setId(1L);

        LoanResponseDto response = LoanResponseDto.builder()
                .id(1L)
                .build();

        when(loanRepository.findById(1L))
                .thenReturn(Optional.of(loan));

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        when(loanRepository.save(loan))
                .thenReturn(loan);

        when(loanMapper.toResponse(loan))
                .thenReturn(response);

        LoanResponseDto result = loanService.updateLoan(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void createLoan_shouldThrowException_whenBookAlreadyBorrowed() {

        LoanRequestDto request = LoanRequestDto.builder()
                .bookId(1L)
                .memberId(1L)
                .build();

        Book book = new Book();
        book.setId(1L);
        book.setAvailable(false);

        Member member = new Member();
        member.setId(1L);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        when(memberRepository.findById(1L))
                .thenReturn(Optional.of(member));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> loanService.createLoan(request)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Book is already borrowed.");

        verify(bookRepository, never()).save(any(Book.class));
        verify(loanRepository, never()).save(any(Loan.class));
    }
}