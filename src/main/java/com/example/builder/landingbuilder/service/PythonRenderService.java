package com.example.builder.landingbuilder.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.builder.landingbuilder.exception.PythonServiceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PythonRenderService {

    private final RestTemplate restTemplate;

    /**
     * Эндпоинт Python-рендера, принимает json_data.
     *
     * В application.properties или через ENV:
     *   python.render.url=http://localhost:8001/render
     *
     * В docker-compose — будет http://python:8000/render
     */
    @Value("${python.render.url:http://localhost:4000/render}")
    private String pythonRenderUrl;

    /**
     * Отправляет JSON проекта в Python и получает сгенерированный HTML.
     * Тело запроса: { "json_data": jsonData }
     * Ответ Python: { "html": "<!DOCTYPE html>...", "css": "..." }
     */
    public RenderResult renderToHtml(String jsonData) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of("json_data", jsonData);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    pythonRenderUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new PythonServiceException(
                        "render",
                        "Python render service returned non-2xx status: " + response.getStatusCode(),
                        null
                );
            }

            Map<String, Object> body = response.getBody();
            Object htmlObj = body.get("html");
            Object cssObj = body.get("css");

            if (!(htmlObj instanceof String html)) {
                throw new PythonServiceException(
                        "render",
                        "Python render service did not return 'html' field",
                        null
                );
            }

            String css = cssObj instanceof String ? (String) cssObj : "";

            return new RenderResult(html, css);

        } catch (PythonServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new PythonServiceException(
                    "render",
                    "Error communicating with Python render service: " + e.getMessage(),
                    e
            );
        }
    }

    public record RenderResult(String html, String css) {}
}
