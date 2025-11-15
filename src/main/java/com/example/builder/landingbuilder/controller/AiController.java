package com.example.builder.landingbuilder.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.builder.landingbuilder.dto.AiGenerateRequest;
import com.example.builder.landingbuilder.dto.AiGenerateResponse;
import com.example.builder.landingbuilder.service.AiService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    /**
     * Этот эндпоинт уже используется фронтом:
     * POST /api/ai/generate  body: { "prompt": "..." }
     */
    @PostMapping("/generate")
    public ResponseEntity<AiGenerateResponse> generate(@Valid @RequestBody AiGenerateRequest request) {
        var model = aiService.generateLandingModel(request.getPrompt());
        return ResponseEntity.ok(new AiGenerateResponse(model));
    }

    /**
     * Простой healthcheck для фронта/админки:
     * GET /api/ai/health → 200 OK или 503
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        boolean ok = aiService.isHealthy();
        return ok
                ? ResponseEntity.ok("OK")
                : ResponseEntity.status(503).body("Python AI service is not available");
    }
}
