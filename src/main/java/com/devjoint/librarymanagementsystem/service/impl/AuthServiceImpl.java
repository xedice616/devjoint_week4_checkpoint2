package com.devjoint.librarymanagementsystem.service.impl;

import com.devjoint.librarymanagementsystem.dto.request.RegisterRequest;
import com.devjoint.librarymanagementsystem.entity.User;
import com.devjoint.librarymanagementsystem.exception.EmailAlreadyExistsException;
import com.devjoint.librarymanagementsystem.repository.UserRepository;
import com.devjoint.librarymanagementsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.devjoint.librarymanagementsystem.dto.request.LoginRequest;
import com.devjoint.librarymanagementsystem.dto.response.AuthenticationResponse;
import com.devjoint.librarymanagementsystem.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A user with this email already exists.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    @Override
    public AuthenticationResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(user.getEmail());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
