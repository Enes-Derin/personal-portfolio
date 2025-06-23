package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IProjectController;
import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import com.enesderin.portfolio.service.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectControllerImpl implements IProjectController {



    @Autowired
    private IProjectService projectService;

    @GetMapping
    @Override
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projectResponses = this.projectService.getAllProjects();
        return ResponseEntity.ok(projectResponses);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<ProjectResponse> getProjectById(Long id) {
        ProjectResponse projectResponse = this.projectService.getProjectById(id);
        return ResponseEntity.ok(projectResponse);
    }

    @PostMapping("/admin")
    @Operation(
            summary = "Yeni proje oluştur",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(implementation = ProjectRequest.class)
                    )
            )
    )
    @Override
    public ResponseEntity<ProjectResponse> createProject(@ModelAttribute ProjectRequest projectRequest) {
        try {
            ProjectResponse projectResponse = projectService.createProject(projectRequest);
            return ResponseEntity.ok(projectResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/admin/{id}")
    @Override
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,@ModelAttribute ProjectRequest projectRequest) {
        try{
            ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
            return ResponseEntity.ok(projectResponse);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    @Override
    public ResponseEntity<String> deleteProject(Long id) {
        this.projectService.deleteProject(id);
        return ResponseEntity.ok().body("Project deleted successfully");
    }
}
