package com.enesderin.portfolio.service;

import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;

import java.io.IOException;
import java.util.List;

public interface IProjectService {
    List<ProjectResponse> getAllProjects();
    ProjectResponse getProjectById(Long id);
    ProjectResponse createProject(ProjectRequest projectRequest) throws IOException;
    ProjectResponse updateProject(Long id, ProjectRequest projectRequest);
    void deleteProject(Long id);
}
