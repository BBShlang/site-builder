package com.example.builder.landingbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Ответ на публикацию проекта.
 * Бэкенд возвращает сгенерированные HTML и CSS,
 * чтобы фронт мог показать лендинг в браузере.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPublishResponse {

    private String status;  // "ok" при успешной публикации
    private String html;    // готовый HTML лендинга
    private String css;     // готовый CSS (может быть пустой строкой)
}
