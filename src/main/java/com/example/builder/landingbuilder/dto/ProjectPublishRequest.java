package com.example.builder.landingbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Запрос на публикацию проекта.
 * Содержит актуальный jsonData из редактора, который нужно сохранить и отрендерить.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPublishRequest {

    /**
     * JSON-модель проекта (например, модель GrapesJS),
     * которую мы сохраняем в проект и передаём в Python-рендер.
     */
    private String jsonData;
}
