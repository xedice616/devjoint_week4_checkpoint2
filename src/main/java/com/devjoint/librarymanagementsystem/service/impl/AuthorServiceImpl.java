package com.devjoint.librarymanagementsystem.service.impl;

import com.devjoint.librarymanagementsystem.dto.request.AuthorRequestDto;
import com.devjoint.librarymanagementsystem.dto.response.AuthorResponseDto;
import com.devjoint.librarymanagementsystem.entity.Author;
import com.devjoint.librarymanagementsystem.exception.ResourceNotFoundException;
import com.devjoint.librarymanagementsystem.mapper.AuthorMapper;
import com.devjoint.librarymanagementsystem.repository.AuthorRepository;
import com.devjoint.librarymanagementsystem.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto requestDto) {

        Author author = authorMapper.toEntity(requestDto);

        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toResponse(savedAuthor);
    }

    @Override
    public AuthorResponseDto getAuthorById(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        return authorMapper.toResponse(author);
    }

    @Override
    public Page<AuthorResponseDto> getAllAuthors(int page, int size, String sortBy, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return authorRepository.findAll(pageable).map(authorMapper::toResponse);
    }

    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto requestDto) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        author.setFirstName(requestDto.getFirstName());
        author.setLastName(requestDto.getLastName());
        author.setEmail(requestDto.getEmail());

        Author updatedAuthor = authorRepository.save(author);

        return authorMapper.toResponse(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        authorRepository.delete(author);
    }
}