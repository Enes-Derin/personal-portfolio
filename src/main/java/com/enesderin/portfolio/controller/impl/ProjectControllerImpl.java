package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.IProjectController;
import com.enesderin.portfolio.dto.ProjectRequest;
import com.enesderin.portfolio.dto.ProjectResponse;
import com.enesderin.portfolio.exception.BaseException;
import com.enesderin.portfolio.exception.ErrorMessage;
import com.enesderin.portfolio.exception.MessageType;
import com.enesderin.portfolio.model.RootEntity;
import com.enesderin.portfolio.service.IProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectControllerImpl extends RestBaseController implements IProjectController {



    @Autowired
    private IProjectService projectService;

    @GetMapping
    @Override
    public RootEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projectResponses = this.projectService.getAllProjects();
        return ok(projectResponses);
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse projectResponse = this.projectService.getProjectById(id);
        return ok(projectResponse);
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
    public RootEntity<ProjectResponse> createProject(@ModelAttribute @Valid ProjectRequest projectRequest) {
        try {
            ProjectResponse projectResponse = projectService.createProject(projectRequest);
            return ok(projectResponse);
        } catch (Exception e) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON,null));
        }
    }

    @PutMapping("/admin/{id}")
    @Override
    public RootEntity<ProjectResponse> updateProject(@PathVariable Long id,@ModelAttribute @Valid ProjectRequest projectRequest) {
        try{
            ProjectResponse projectResponse = projectService.updateProject(id, projectRequest);
            return ok(projectResponse);
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTİON,null));
        }
    }

    @DeleteMapping("/admin/delete/{id}")
    @Override
    public RootEntity<String> deleteProject(@PathVariable Long id) {
        this.projectService.deleteProject(id);
        return ok("Project deleted successfully");
    }
}
