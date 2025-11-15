package com.example.builder.landingbuilder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.builder.landingbuilder.model.Template;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
    List<Template> findByUserId(UUID userId);
}
