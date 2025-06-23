package com.enesderin.portfolio.service.impl;

import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;
import com.enesderin.portfolio.model.Skill;
import com.enesderin.portfolio.repository.SkillRepository;
import com.enesderin.portfolio.service.ISkillService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements ISkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<SkillResponse> getSkills() {
        List<Skill> skillList = this.skillRepository.findAll();
        List<SkillResponse> skillResponseList = new ArrayList<>();
        for (Skill skill : skillList) {
            SkillResponse skillResponse = new SkillResponse();
            BeanUtils.copyProperties(skill, skillResponse);
            skillResponseList.add(skillResponse);
        }
        return skillResponseList;
    }


    @Override
    public SkillResponse createSkill(SkillRequest skillRequest) {
        Skill skill = new Skill();
        BeanUtils.copyProperties(skillRequest, skill);
        Skill savedSkill = this.skillRepository.save(skill);
        SkillResponse skillResponse = new SkillResponse();
        BeanUtils.copyProperties(savedSkill, skillResponse);
        return skillResponse;
    }



    @Override
    public void deleteSkill(Long id) {
        Optional<Skill> optional = this.skillRepository.findById(id);
        if (optional.isPresent()) {
            Skill skill = optional.get();
            this.skillRepository.delete(skill);
        }

    }
}
