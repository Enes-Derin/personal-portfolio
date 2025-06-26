package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import com.enesderin.portfolio.exception.BaseException;
import com.enesderin.portfolio.exception.ErrorMessage;
import com.enesderin.portfolio.exception.MessageType;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;

    // Kaydetme klasörü
    private final String uploadDir = "src/main/resources/static/img";

    @Override
    public List<ProjectResponse> getAllProjects() {
        List<Project> projectList = projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for (Project project : projectList) {
            ProjectResponse response = new ProjectResponse();
            BeanUtils.copyProperties(project, response);
            projectResponses.add(response);
        }

        return projectResponses;
    }

    @Override
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXİST, id.toString())));

        ProjectResponse response = new ProjectResponse();
        BeanUtils.copyProperties(project, response);
        return response;
    }

    @Override
    public ProjectResponse createProject(ProjectRequest projectRequest) {
        try {
            String fileName = UUID.randomUUID() + "_" + projectRequest.getImageUrl().getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/img");
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, projectRequest.getImageUrl().getBytes());

            Project project = new Project();
            BeanUtils.copyProperties(projectRequest, project);
            project.setImageUrl("/img/" + fileName); // 🔥 Doğru public yol

            projectRepository.save(project);

            ProjectResponse projectResponse = new ProjectResponse();
            BeanUtils.copyProperties(project, projectResponse);
            return projectResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest projectRequest) {
        Optional<Project> optional = projectRepository.findById(id);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXİST, id.toString()));
        }

        Project project = optional.get();
        BeanUtils.copyProperties(projectRequest, project, "id", "imageUrl"); // imageUrl ayrı işlenecek

        if (projectRequest.getImageUrl() != null && !projectRequest.getImageUrl().isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + projectRequest.getImageUrl().getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, projectRequest.getImageUrl().getBytes());
                project.setImageUrl("/img/" + fileName);
            } catch (IOException e) {
                throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON, "Görsel güncelleme hatası"));
            }
        }

        projectRepository.save(project);

        ProjectResponse response = new ProjectResponse();
        BeanUtils.copyProperties(project, response);
        return response;
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXİST, id.toString())));
        projectRepository.delete(project);
    }
}
