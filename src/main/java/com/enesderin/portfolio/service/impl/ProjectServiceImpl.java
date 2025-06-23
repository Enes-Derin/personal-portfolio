package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import com.enesderin.portfolio.model.Project;
import com.enesderin.portfolio.repository.ProjectRepository;
import com.enesderin.portfolio.service.IProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;

    private String uploadDir= "uploads/";

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projectList= this.projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for (Project project : projectList) {
            ProjectResponse projectResponse = new ProjectResponse();
            BeanUtils.copyProperties(project, projectResponse);
            projectResponses.add(projectResponse);
        }
        return projectResponses;
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Optional<Project> optional = this.projectRepository.findById(id);
        if (optional.isPresent()) {
            ProjectResponse projectResponse = new ProjectResponse();
            BeanUtils.copyProperties(optional.get(), projectResponse);
            return projectResponse;
        }
        // Exception
        return null;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        String fileName= UUID.randomUUID() + "_" + projectRequest.getImageUrl().getOriginalFilename();
        Path filePath= Paths.get(uploadDir , fileName);
        try{
            Files.createDirectories(filePath.getParent());
            Files.write(filePath,projectRequest.getImageUrl().getBytes());
            Project project = new Project();
            BeanUtils.copyProperties(projectRequest, project);
            project.setImageUrl("/"+uploadDir+fileName);
            projectRepository.save(project);
            ProjectResponse projectResponse = new ProjectResponse();
            BeanUtils.copyProperties(project, projectResponse);
            return projectResponse;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Optional<Project> optional = this.projectRepository.findById(id);
        if (optional.isPresent()) {
            Project project = optional.get();
            BeanUtils.copyProperties(projectRequest, project);
            if (projectRequest.getImageUrl() != null && !projectRequest.getImageUrl().isEmpty()) {
                String fileName= UUID.randomUUID() + "_" + projectRequest.getImageUrl().getOriginalFilename();
                Path filePath = Paths.get(uploadDir , fileName);
                try{
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath,projectRequest.getImageUrl().getBytes());
                    project.setImageUrl("/"+uploadDir+fileName);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            projectRepository.save(project);
            ProjectResponse projectResponse = new ProjectResponse();
            BeanUtils.copyProperties(project, projectResponse);
            return projectResponse;
        }
        return null;
    }

    @Override
    public void deleteProject(Long id) {
        Optional<Project> optional = this.projectRepository.findById(id);
        if (optional.isPresent()) {
            this.projectRepository.delete(optional.get());
        }
    }
}
