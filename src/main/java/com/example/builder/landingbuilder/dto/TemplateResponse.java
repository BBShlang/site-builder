package com.example.builder.landingbuilder.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TemplateResponse {
    private UUID id;
    private String name;
    private String jsonData;
}
