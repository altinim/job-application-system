package com.example.careerify.service;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.common.mappers.ExperienceMapper;
import com.example.careerify.model.Experience;
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

import java.util.List;
import java.util.UUID;
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private UserRepository userRepository;
    private ExperienceRepository experienceRepository;
    private ExperienceMapper experienceMapper;
    private final JwtService jwtService;

    public ExperienceServiceImpl(UserRepository userRepository, ExperienceRepository experienceRepository, ExperienceMapper experienceMapper, JwtService jwtService){
        this.userRepository = userRepository;
        this.experienceRepository = experienceRepository;
        this.experienceMapper = experienceMapper;
        this.jwtService = jwtService;

    }

    @Override
    public void createExperience(ExperienceDTO experienceDTO) {
        UUID currentUserId = jwtService.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            Experience experience = experienceMapper.mapDTOToExperience(experienceDTO);
            experience.setUser(user);
            experienceRepository.save(experience);
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
    public List<ExperienceDTO> getExperiencesByUserId(UUID userId) {
        List<Experience> userExperiences = experienceRepository.findAllByUserId(userId);
        return userExperiences.stream()
                .map(experienceMapper::mapExperienceToDTO)
                .toList();
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
