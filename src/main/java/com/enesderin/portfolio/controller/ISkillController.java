package com.enesderin.portfolio.controller;

import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISkillController {
    ResponseEntity<List<SkillResponse>> getSkills();
    ResponseEntity<SkillResponse> createSkill(SkillRequest skillRequest);
    ResponseEntity<String> deleteSkill(Long id);

}
