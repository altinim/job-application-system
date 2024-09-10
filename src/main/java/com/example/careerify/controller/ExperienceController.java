package com.example.careerify.controller;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.service.ExperienceService;
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
@RequestMapping("api/v1/experience")
public class ExperienceController {
    private final ExperienceService experienceService;
    
    @Autowired
    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<Object> createExperience(@RequestBody ExperienceDTO experienceDTO) {
        try {
            experienceService.createExperience(experienceDTO);
            return new ResponseEntity<>( HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating experience: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getExperienceById(@PathVariable UUID id) {
        ExperienceDTO experienceDTO = experienceService.getExperienceById(id);
        return new ResponseEntity<>(experienceDTO, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<Page<ExperienceDTO>> getAllExperience(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<ExperienceDTO> experiences = experienceService.getAllExperiences(pageable);
        return new ResponseEntity<>(experiences, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteExperience(@PathVariable UUID id) {
        experienceService.deleteExperience(id);
        return new ResponseEntity<>("Experience deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("user/{userId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ExperienceDTO>> getUserExperiences(@PathVariable UUID userId) {

        List<ExperienceDTO> userExperiences = experienceService.getExperiencesByUserId(userId);
        return new ResponseEntity<>(userExperiences, HttpStatus.OK);
    }
    
}
