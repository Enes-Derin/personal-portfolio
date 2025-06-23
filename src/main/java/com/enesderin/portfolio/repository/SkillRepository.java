package com.enesderin.portfolio.repository;

import com.enesderin.portfolio.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
