package com.devjoint.librarymanagementsystem.controller;

import com.devjoint.librarymanagementsystem.dto.request.RegisterRequest;
import com.devjoint.librarymanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.devjoint.librarymanagementsystem.dto.request.LoginRequest;
import com.devjoint.librarymanagementsystem.dto.response.AuthenticationResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(requestDto));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest requestDto) {

        return ResponseEntity.ok(authService.login(requestDto));
    }
}