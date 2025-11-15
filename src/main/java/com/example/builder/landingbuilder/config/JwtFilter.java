package com.example.builder.landingbuilder.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.security.UserDetailsImpl;
import com.example.builder.landingbuilder.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    @Value("${jwt.secret}")
    private String jwtSecret; // Должен совпадать с тем, что используется при генерации токена

    public JwtFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret.getBytes("UTF-8"))
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    User user = userService.findByUsername(username);
                    if (user != null) {
                        UserDetailsImpl userDetails = new UserDetailsImpl(user);
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, user.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception e) {
                // Логируем ошибку для отладки
                System.out.println("JWT parsing error: " + e.getMessage());
                // Просто пропускаем запрос дальше, без установки контекста, чтобы вернуть 403 на уровне контроллера
            }
        }

        filterChain.doFilter(request, response);
    }
}
