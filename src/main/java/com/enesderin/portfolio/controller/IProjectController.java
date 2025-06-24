package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import com.enesderin.portfolio.model.RootEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProjectController {

    RootEntity<List<ProjectResponse>> getAllProjects();
    RootEntity<ProjectResponse> getProjectById(Long id);
    RootEntity<ProjectResponse> createProject(ProjectRequest projectRequest);
    RootEntity<ProjectResponse> updateProject(Long id, ProjectRequest projectRequest);
    RootEntity<String> deleteProject(Long id);
}
