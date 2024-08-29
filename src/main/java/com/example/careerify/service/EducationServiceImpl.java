package com.example.careerify.service;

import com.example.careerify.common.dto.EducationDTO;
import com.example.careerify.common.mappers.EducationMapper;
import com.example.careerify.model.Applicant;
import com.example.careerify.model.Education;
import com.example.careerify.repository.ApplicantRepository;
import com.example.careerify.repository.EducationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EducationServiceImpl implements EducationService{

    private ApplicantRepository applicantRepository;
    private EducationMapper educationMapper;
    private EducationRepository educationRepository;

    public EducationServiceImpl(ApplicantRepository applicantRepository, EducationMapper educationMapper, EducationRepository educationRepository) {
        this.applicantRepository = applicantRepository;
        this.educationMapper = educationMapper;
        this.educationRepository = educationRepository;
    }

    @Override
    public EducationDTO createEducation(EducationDTO educationDTO) {
        try {
            Education education = educationMapper.mapDTOToEducation(educationDTO);

            Education savedEducation = educationRepository.save(education);

            return educationMapper.mapEducationToDTO(savedEducation);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error creating education. Duplicate entry.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating education. Illegal argument.", e);
        }
    }
    @Transactional
    @Override
    public EducationDTO createEducationForApplicant(UUID applicantId, EducationDTO educationDTO) {
        try {
            Applicant applicant = applicantRepository.findById(applicantId)
                    .orElseThrow(() -> new EntityNotFoundException("Applicant not found with ID: " + applicantId));

            Education education = educationMapper.mapDTOToEducation(educationDTO);
            education.setApplicant(applicant);

            Education savedEducation = educationRepository.save(education);

            return educationMapper.mapEducationToDTO(savedEducation);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating education. Illegal argument.", e);
        }
    }

    @Override
    public Page<EducationDTO> getAllEducations(Pageable pageable) {
        Page<Education> educations = educationRepository.findAll(pageable);
        return educations.map(educationMapper::mapEducationToDTO);
    }

    @Override
    public void updateEducation(UUID id, EducationDTO updateDTO) {
        Education education = educationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with ID: " + id));

        educationMapper.updateEducationFromDTO(updateDTO, education);

        educationRepository.save(education);
    }

    @Override
    public void deleteEducation(UUID educationId) {
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new EntityNotFoundException("Education not found with ID: " + educationId));

        educationRepository.delete(education);

    }
}
