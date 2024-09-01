package com.example.careerify.service;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.mappers.ExperienceMapper;
import com.example.careerify.model.User;
import com.example.careerify.model.Experience;
import com.example.careerify.repository.ExperienceRepository;
import com.example.careerify.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private UserRepository userRepository;
    private ExperienceRepository experienceRepository;
    private ExperienceMapper experienceMapper;

    public ExperienceServiceImpl(UserRepository userRepository, ExperienceRepository experienceRepository, ExperienceMapper experienceMapper){
        this.userRepository = userRepository;
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
    }

    @Override
    public ExperienceDTO createExperience(ExperienceDTO experienceDTO) {
        try {
            Experience experience = experienceMapper.mapDTOToExperience(experienceDTO);

            Experience savedExperience = experienceRepository.save(experience);

            return experienceMapper.mapExperienceToDTO(savedExperience);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error creating experience. Duplicate entry.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating experience. Illegal argument.", e);
        }
    }
    @Override
    public ExperienceDTO getExperienceById(UUID id) {
        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Experience not found with ID: " + id));
        return experienceMapper.mapExperienceToDTO(experience);
    }

    @Override
    public Page<ExperienceDTO> getAllExperiences(Pageable pageable) {
        Page<Experience> experiences = experienceRepository.findAll(pageable);
        return experiences.map(experienceMapper::mapExperienceToDTO);
    }

    @Override
    public void deleteExperience(UUID experienceId) {
        Experience existingExperience = experienceRepository.findById(experienceId)
                .orElseThrow(() -> new EntityNotFoundException("Experience not found with ID: " + experienceId));

        experienceRepository.delete(existingExperience);
    }
}
