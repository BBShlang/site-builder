package com.example.builder.landingbuilder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.builder.landingbuilder.dto.CreateProjectRequest;
import com.example.builder.landingbuilder.dto.ProjectPublishRequest;
import com.example.builder.landingbuilder.dto.ProjectPublishResponse;
import com.example.builder.landingbuilder.model.Project;
import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.security.UserDetailsImpl;
import com.example.builder.landingbuilder.service.ProjectService;
import com.example.builder.landingbuilder.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;

    private User getCurrentUser(Authentication auth) {
        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userDetails.getUser();
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getUserProjects() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);

        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(projectService.findByUser(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable UUID id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);
        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        Project project = projectService.findById(id);
        if (project == null || !project.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody CreateProjectRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);
        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        Project project = new Project();
        project.setUser(currentUser);
        project.setJsonData(request.getJsonData());

        return ResponseEntity.ok(projectService.createProject(project));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable UUID id, @RequestBody CreateProjectRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);
        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        Project project = projectService.findById(id);
        if (project == null || !project.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        project.setJsonData(request.getJsonData());
        return ResponseEntity.ok(projectService.createProject(project));
    }

    /**
     * Публикация проекта:
     * - проверяем, что проект принадлежит текущему пользователю;
     * - обновляем jsonData;
     * - вызываем Python-рендер;
     * - возвращаем сгенерированный HTML.
     */
    @PostMapping("/{id}/publish")
    public ResponseEntity<ProjectPublishResponse> publishProject(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectPublishRequest request
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);
        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        Project project = projectService.findById(id);
        if (project == null || !project.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        var renderResult = projectService.publishProject(project, request.getJsonData());

        ProjectPublishResponse response = new ProjectPublishResponse(
                "ok",
                renderResult.html(), null
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = getCurrentUser(auth);
        if (currentUser == null) {
            return ResponseEntity.status(403).build();
        }

        Project project = projectService.findById(id);
        if (project == null || !project.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }

        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
