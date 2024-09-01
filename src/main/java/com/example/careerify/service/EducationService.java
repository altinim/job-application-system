package com.example.careerify.service;

import com.example.careerify.common.dto.EducationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EducationService {

    EducationDTO createEducation(EducationDTO educationDTO);
    Page<EducationDTO> getAllEducations(Pageable pageable);
    void updateEducation(UUID id, EducationDTO updateDTO);
    void deleteEducation(UUID educationId);


}
