package com.example.builder.landingbuilder.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.builder.landingbuilder.dto.LoginRequest;
import com.example.builder.landingbuilder.dto.TokenRequest;
import com.example.builder.landingbuilder.dto.TokenResponse;
import com.example.builder.landingbuilder.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest request) throws Exception {
        return tokenService.generateToken(
                new TokenRequest(
                        request.getUsername(),
                        request.getPassword()
                )
        );
    }
}