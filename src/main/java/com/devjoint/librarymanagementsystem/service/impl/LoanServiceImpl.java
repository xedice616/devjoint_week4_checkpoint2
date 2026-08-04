package com.devjoint.librarymanagementsystem.service.impl;

import com.devjoint.librarymanagementsystem.dto.request.LoanRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.LoanResponseDto;
import com.devjoint.librarymanagementsystem.entity.Book;
import com.devjoint.librarymanagementsystem.entity.Loan;
import com.devjoint.librarymanagementsystem.entity.Member;
import com.devjoint.librarymanagementsystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagementsystem.mapper.LoanMapper;
import com.devjoint.librarymanagementsystem.repository.BookRepository;
import com.devjoint.librarymanagementsystem.repository.LoanRepository;
import com.devjoint.librarymanagementsystem.repository.MemberRepository;
import com.devjoint.librarymanagementsystem.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final LoanMapper loanMapper;
    @Transactional

    @Override
    public LoanResponseDto createLoan(LoanRequestDto requestDto) {

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + requestDto.getBookId()));

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + requestDto.getMemberId()));

        Loan loan = loanMapper.toEntity(requestDto);

        loan.setBook(book);
        loan.setMember(member);

        loan.setBorrowDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusDays(14));
        loan.setReturnDate(null);

        if (!book.getAvailable()) {
            throw new IllegalStateException("Book is already borrowed.");
        }

        book.setAvailable(false);

        bookRepository.save(book);




        Loan savedLoan = loanRepository.save(loan);

        return loanMapper.toResponse(savedLoan);
    }

    @Override
    public LoanResponseDto getLoanById(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan not found with id: " + id));

        return loanMapper.toResponse(loan);
    }

    @Override
    public Page<LoanResponseDto> getAllLoans(
            int page,
            int size,
            String sortBy,
            String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return loanRepository.findAll(pageable)
                .map(loanMapper::toResponse);
    }

    @Override
    public LoanResponseDto updateLoan(Long id, LoanRequestDto requestDto) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan not found with id: " + id));

        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + requestDto.getBookId()));

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + requestDto.getMemberId()));

        loan.setBook(book);
        loan.setMember(member);

        Loan updatedLoan = loanRepository.save(loan);

        return loanMapper.toResponse(updatedLoan);
    }

    @Override
    public void deleteLoan(Long id) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Loan not found with id: " + id));

        loanRepository.delete(loan);
    }
}