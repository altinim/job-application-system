package com.example.careerify.service;

import com.example.careerify.common.dto.EducationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface EducationService {

    EducationDTO createEducation(EducationDTO educationDTO);
    Page<EducationDTO> getAllEducations(Pageable pageable);
    List<EducationDTO> getEducationsByUserId(UUID userId);
    void updateEducation(UUID id, EducationDTO updateDTO);
    void deleteEducation(UUID educationId);


}
