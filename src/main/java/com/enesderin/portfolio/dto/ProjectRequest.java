package com.enesderin.portfolio.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @NotNull
    private MultipartFile imageUrl;
    @NotEmpty
    private String githubUrl;
}
