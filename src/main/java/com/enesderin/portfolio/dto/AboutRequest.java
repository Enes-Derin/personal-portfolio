package com.enesderin.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutRequest {
    private String description;
    private String cvUrl;
    private MultipartFile imageUrl;
}
