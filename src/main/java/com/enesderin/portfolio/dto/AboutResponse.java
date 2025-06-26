package com.enesderin.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AboutResponse {
    private int id;
    private String description;
    private String cvUrl;
    private String imageUrl;
}
