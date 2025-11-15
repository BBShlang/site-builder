package com.example.builder.landingbuilder.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectPublishRequest {

    /**
     * JSON проекта, который приходит с фронта при публикации.
     */
    @NotBlank
    private String jsonData;
}

