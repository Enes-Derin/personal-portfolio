package com.enesderin.portfolio.dto;

import lombok.Data;

@Data
public class SkillResponse {
    private Long id;
    private String name;
    private String iconUrl;
    private String category;
}
