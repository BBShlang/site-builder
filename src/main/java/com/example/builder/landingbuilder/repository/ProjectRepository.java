package com.example.builder.landingbuilder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.builder.landingbuilder.model.Project;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByUserId(UUID userId);
}
