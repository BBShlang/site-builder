// ProjectPublishResponse.java
package com.example.builder.landingbuilder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectPublishResponse {
    private String status;       // "ok"
    private String url;          // публичная ссылка (или null, если ещё нет)
    private String message;      // человекочитаемое сообщение
}
