package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;
import com.enesderin.portfolio.model.RootEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISkillController {
    RootEntity<List<SkillResponse>> getSkills();
    RootEntity<SkillResponse> createSkill(SkillRequest skillRequest);
    RootEntity<String> deleteSkill(Long id);

}
