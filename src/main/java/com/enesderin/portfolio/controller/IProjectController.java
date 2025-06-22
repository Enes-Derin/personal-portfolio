package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProjectController {

    ResponseEntity<List<ProjectResponse>> getAllProjects();
    ResponseEntity<ProjectResponse> getProjectById(Long id);
    ResponseEntity<ProjectResponse> createProject(ProjectRequest projectRequest);
    ResponseEntity<ProjectResponse> updateProject(Long id, ProjectRequest projectRequest);
    ResponseEntity<String> deleteProject(Long id);
}
