package com.example.careerify.controller;

import com.example.careerify.common.dto.EducationDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/educations")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<Object> createEducation(@RequestBody EducationDTO educationDTO) {
        try {
            EducationDTO createdEducation = educationService.createEducation(educationDTO);
            return new ResponseEntity<>(createdEducation, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating Education: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<Page<EducationDTO>> getAllEducations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size);
        Page<EducationDTO> educations = educationService.getAllEducations(pageable);
        return new ResponseEntity<>(educations, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEducation(@PathVariable UUID id, @RequestBody EducationDTO updateDTO) {
        educationService.updateEducation(id, updateDTO);
        return new ResponseEntity<>("Education updated successfully", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEduation(@PathVariable UUID id) {
        educationService.deleteEducation(id);
        return new ResponseEntity<>("Education deleted successfully", HttpStatus.NO_CONTENT);
    }

}





