package com.enesderin.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {
    private String title;
    private String description;
    private MultipartFile imageUrl;
    private String githubUrl;
}
