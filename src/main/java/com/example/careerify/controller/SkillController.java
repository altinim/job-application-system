package com.example.careerify.controller;
import com.example.careerify.common.dto.SkillDTO;
import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.SkillDTO;
import com.example.careerify.service.SkillService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/skills")
public class SkillController {
    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<Object> createSkill(@RequestBody SkillDTO skillDTO) {
        try {
            SkillDTO createdSkill = skillService.createSkill(skillDTO);
            return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating skill: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("user/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<SkillDTO>> getUserSkills(@PathVariable UUID userId) {

        List<SkillDTO> userSkills = skillService.getSkillsByUserId(userId);
        return new ResponseEntity<>(userSkills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getSkillById(@PathVariable UUID id) {
        SkillDTO skillDTO = skillService.getSkillById(id);
        return new ResponseEntity<>(skillDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SkillDTO>> getAllSkills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<SkillDTO> skills = skillService.getAllSkills(pageable);
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSkill(@PathVariable UUID id) {
        skillService.deleteSkill(id);
        return new ResponseEntity<>("Skill deleted successfully", HttpStatus.NO_CONTENT);
    }
}
