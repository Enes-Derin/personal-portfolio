package com.enesderin.portfolio.controller.impl;

import com.enesderin.portfolio.controller.ISkillController;
import com.enesderin.portfolio.dto.SkillRequest;
import com.enesderin.portfolio.dto.SkillResponse;
import com.enesderin.portfolio.model.RootEntity;
import com.enesderin.portfolio.service.ISkillService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
@AllArgsConstructor
public class SkillControllerImpl extends RestBaseController implements ISkillController {

    private ISkillService skillService;

    @GetMapping
    @Override
    public RootEntity<List<SkillResponse>> getSkills() {
        return ok(skillService.getSkills());
    }

    @PostMapping("/admin")
    @Override
    public RootEntity<SkillResponse> createSkill(@RequestBody @Valid SkillRequest skillRequest) {
        return ok(skillService.createSkill(skillRequest));
    }

    @DeleteMapping("/admin/{id}")
    @Override
    public RootEntity<String> deleteSkill(@PathVariable Long id) {
        return ok("Delete skill with id " + id);
    }
}
