package com.example.builder.landingbuilder.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AiGenerateRequest {

    @NotBlank
    private String prompt;
}

