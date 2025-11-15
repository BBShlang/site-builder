package com.example.builder.landingbuilder.service;

import com.example.builder.landingbuilder.config.JwtUtil;
import com.example.builder.landingbuilder.dto.TokenRequest;
import com.example.builder.landingbuilder.dto.TokenResponse;
import com.example.builder.landingbuilder.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    public TokenService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }
    public TokenResponse generateToken(TokenRequest tokenRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tokenRequest.getUsername(),
                            tokenRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", tokenRequest.getUsername(), e);
            throw new BadCredentialsException("Неверные учетные данные", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());
        String token = jwtUtil.createJwtToken(Collections.emptyMap(), userDetails.getUsername());
        return TokenResponse.builder()
                .token(token)
                .build();
    }
}

