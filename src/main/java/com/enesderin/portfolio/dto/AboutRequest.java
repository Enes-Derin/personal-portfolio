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
public class AboutRequest {
    @NotEmpty
    private String description;
    @NotEmpty
    private String cvUrl;
    @NotNull
    private MultipartFile imageUrl;
}
