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
public class AiService {

    private final RestTemplate restTemplate;

    /**
     * FastAPI эндпоинт: POST /ai/process.
     *
     * В application.properties или через ENV:
     *   python.ai.url=http://localhost:8001/ai/process
     *
     * В docker-compose — будет http://python:8000/ai/process
     */
    @Value("${python.ai.url:http://localhost:4000/ai/process}")
    private String pythonAiUrl;

    /**
     * Вызывает Python /ai/process.
     * На вход: prompt от фронта.
     * На выход: JSON-модель лендинга для GrapesJS.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> generateLandingModel(String prompt) {
        try {
            Map<String, Object> requestBody = Map.of("text", prompt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    pythonAiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new PythonServiceException(
                        "ai",
                        "Python AI service returned non-2xx status: " + response.getStatusCode(),
                        null
                );
            }

            Map<String, Object> body = response.getBody();
            Object data = body.get("data");
            if (!(data instanceof Map<?, ?> dataMapRaw)) {
                throw new PythonServiceException(
                        "ai",
                        "Python AI service returned unexpected 'data' format",
                        null
                );
            }

            Map<String, Object> dataMap = (Map<String, Object>) dataMapRaw;
            Object modelObj = dataMap.get("model");

            // Ожидаем, что Python вернёт {data: {model: {...}}}
            if (modelObj instanceof Map<?, ?> modelRaw) {
                return (Map<String, Object>) modelRaw;
            }

            // Если model отсутствует, но data сам по себе модель — отдадим его
            return dataMap;

        } catch (PythonServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new PythonServiceException(
                    "ai",
                    "Error communicating with Python AI service: " + e.getMessage(),
                    e
            );
        }
    }

    /**
     * Простейший healthcheck для /api/ai/health
     */
    public boolean isHealthy() {
        try {
            Map<String, Object> requestBody = Map.of("text", "ping");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    pythonAiUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
