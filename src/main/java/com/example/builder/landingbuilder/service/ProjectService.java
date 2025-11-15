package com.example.builder.landingbuilder.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.builder.landingbuilder.model.Project;
import com.example.builder.landingbuilder.model.User;
import com.example.builder.landingbuilder.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final PythonRenderService pythonRenderService;

    public List<Project> findByUser(User user) {
        return projectRepository.findByUserId(user.getId());
    }

    public Project findById(UUID id) {
        return projectRepository.findById(id).orElse(null);
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }

    /**
     * Обновляет jsonData проекта и вызывает Python-рендер.
     */
    @Transactional
    public PythonRenderService.RenderResult publishProject(Project project, String jsonData) {
        project.setJsonData(jsonData);
        projectRepository.save(project);
        return pythonRenderService.renderToHtml(jsonData);
    }
}
