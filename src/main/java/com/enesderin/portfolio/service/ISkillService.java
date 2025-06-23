package com.enesderin.portfolio.service;

import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;

import java.util.List;

public interface ISkillService {
    List<SkillResponse> getSkills();
    SkillResponse createSkill(SkillRequest skillRequest);
    void deleteSkill(Long id);
}
