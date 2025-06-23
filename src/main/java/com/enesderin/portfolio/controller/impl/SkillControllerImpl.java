package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.ISkillController;
import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;
import com.enesderin.portfolio.service.ISkillService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@AllArgsConstructor
public class SkillControllerImpl implements ISkillController {

    private ISkillService skillService;

    @GetMapping
    @Override
    public ResponseEntity<List<SkillResponse>> getSkills() {
        return ResponseEntity.ok(skillService.getSkills());
    }

    @PostMapping("/admin")
    @Override
    public ResponseEntity<SkillResponse> createSkill(@RequestBody SkillRequest skillRequest) {
        return ResponseEntity.ok(skillService.createSkill(skillRequest));
    }

    @DeleteMapping("/admin/{id}")
    @Override
    public ResponseEntity<String> deleteSkill(@PathVariable Long id) {
        return ResponseEntity.ok("Delete skill with id " + id);
    }
}
