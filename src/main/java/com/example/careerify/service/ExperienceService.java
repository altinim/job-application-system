package com.example.careerify.service;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.ExperienceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface ExperienceService {
    ExperienceDTO createExperience(ExperienceDTO experienceDTO);
    ExperienceDTO getExperienceById (UUID id);
    Page<ExperienceDTO> getAllExperiences (Pageable pageable);
    void deleteExperience(UUID experienceId);
    

}
