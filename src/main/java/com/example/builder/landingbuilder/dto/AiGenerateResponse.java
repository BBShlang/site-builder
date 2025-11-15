package com.example.builder.landingbuilder.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AiGenerateResponse {

    /**
     * JSON-модель проекта для GrapesJS (pages, assets, styles и т.п.)
     */
    private Map<String, Object> model;
}
