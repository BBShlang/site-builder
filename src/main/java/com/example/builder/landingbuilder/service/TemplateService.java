package com.example.builder.landingbuilder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.builder.landingbuilder.dto.CreateTemplateRequest;
import com.example.builder.landingbuilder.dto.TemplateResponse;
import com.example.builder.landingbuilder.model.Template;
import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.repository.TemplateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository repo;

    public List<TemplateResponse> getAllTemplates(User user) {
        return repo.findByUserId(user.getId())
            .stream()
            .map(t -> new TemplateResponse(
                t.getId(),
                t.getName(),
                t.getJsonData()
            ))
            .toList();
    }

    public TemplateResponse create(User user, CreateTemplateRequest req) {
        Template t = new Template();
        t.setUser(user);
        t.setName(req.getName());
        t.setJsonData(req.getJsonData());
        repo.save(t);
        return new TemplateResponse(t.getId(), t.getName(), t.getJsonData());
    }
}

