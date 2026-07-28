package com.devjoint.librarymanagementsystem.service;

import com.devjoint.librarymanagementsystem.dto.request.LoginRequest;
import com.devjoint.librarymanagementsystem.dto.request.RegisterRequest;
import com.devjoint.librarymanagementsystem.dto.response.AuthenticationResponse;

public interface AuthService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);
}