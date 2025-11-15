package com.example.builder.landingbuilder.service;

import org.springframework.stereotype.Service;

import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
