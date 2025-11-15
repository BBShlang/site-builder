package com.example.builder.landingbuilder.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.builder.landingbuilder.dto.CreateTemplateRequest;
import com.example.builder.landingbuilder.dto.TemplateResponse;
import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.security.UserDetailsImpl;
import com.example.builder.landingbuilder.service.TemplateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    private User getUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserDetailsImpl u) return u.getUser();
        return null;
    }

    @GetMapping
    public List<TemplateResponse> getTemplates() {
        return templateService.getAllTemplates(getUser());
    }

    @PostMapping
    public TemplateResponse create(@RequestBody CreateTemplateRequest req) {
        return templateService.create(getUser(), req);
    }
}

