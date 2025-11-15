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
public class SitePublishService {

    private final RestTemplate restTemplate;

    @Value("${publisher.url}")
    private String publisherUrl; // например: http://localhost:3000/publish-site

    /**
     * Отправляет HTML+CSS в Node-микросервис публикации и возвращает публичный URL.
     */
    public String publishSite(String html, String css, String siteName) {
        try {
            Map<String, Object> body = Map.of(
                    "htmlContent", html,
                    "cssContent", css,
                    "siteName", siteName
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    publisherUrl,
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new PythonServiceException(
                        "publisher",
                        "Publisher service returned non-2xx status: " + response.getStatusCode(),
                        null
                );
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> respBody = response.getBody();

            Object successObj = respBody.get("success");
            Object urlObj = respBody.get("url");

            boolean success = true;
            if (successObj instanceof Boolean b) {
                success = b;
            } else if (successObj instanceof String s) {
                success = Boolean.parseBoolean(s);
            }

            if (!success) {
                throw new PythonServiceException(
                        "publisher",
                        "Publisher service reported failure",
                        null
                );
            }

            if (urlObj instanceof String url && !url.isBlank()) {
                return url;
            }

            throw new PythonServiceException(
                    "publisher",
                    "Publisher service response did not contain url",
                    null
            );
        } catch (PythonServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PythonServiceException(
                    "publisher",
                    "Error communicating with publisher service: " + ex.getMessage(),
                    ex
            );
        }
    }
}
