package com.enesderin.portfolio.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SkillRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String iconUrl;
    @NotEmpty
    private String category;
}
