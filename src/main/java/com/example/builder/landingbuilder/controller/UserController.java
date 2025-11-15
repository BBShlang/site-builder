package com.example.builder.landingbuilder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.builder.landingbuilder.dto.RegisterRequest;
import com.example.builder.landingbuilder.dto.TokenRequest;
import com.example.builder.landingbuilder.dto.TokenResponse;
import com.example.builder.landingbuilder.model.Role;
import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.repository.UserRepository;
import com.example.builder.landingbuilder.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService; // TokenService добавлен

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenResponse register(@RequestBody @Valid RegisterRequest request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username уже занят");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email уже занят");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        userRepository.save(user);

        // Генерация JWT сразу после регистрации
        return tokenService.generateToken(
            new TokenRequest(request.getUsername(), request.getPassword())
        );
    }
}
