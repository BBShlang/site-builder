package com.example.builder.landingbuilder.exception;

public class PythonServiceException extends RuntimeException {

    private final String service; // "ai" или "render"

    public PythonServiceException(String service, String message, Throwable cause) {
        super(message, cause);
        this.service = service;
    }

    public String getService() {
        return service;
    }
}

