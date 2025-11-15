package com.example.builder.landingbuilder.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ProjectResponse {
    private UUID id;
    private String name;
    private String jsonData;
}

