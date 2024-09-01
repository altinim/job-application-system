package com.example.careerify.service;
import com.example.careerify.common.dto.SkillDTO;
import com.example.careerify.common.mappers.SkillMapper;
import com.example.careerify.model.User;
import com.example.careerify.model.Skill;
import com.example.careerify.repository.SkillRepository;
import com.example.careerify.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class SkillServiceImpl implements SkillService {
   final  private SkillRepository skillRepository;
    final private SkillMapper skillMapper;
    final private UserRepository userRepository;

    public SkillServiceImpl(SkillRepository skillRepository, SkillMapper skillMapper, UserRepository userRepository){
        this.skillRepository = skillRepository;
        this.skillMapper = skillMapper;
        this.userRepository = userRepository;
    }

    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {
        try {
            Skill skill = skillMapper.mapDTOToSkill(skillDTO);

            Skill savedSkill = skillRepository.save(skill);

            return skillMapper.mapSkillToDTO(savedSkill);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error creating skill. Duplicate entry.", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating skill. Illegal argument.", e);
        }
    }

    @Override
    public SkillDTO getSkillById(UUID id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with ID: " + id));
        return skillMapper.mapSkillToDTO(skill);
    }

    @Override
    public Page<SkillDTO> getAllSkills(Pageable pageable) {
        Page<Skill> skills = skillRepository.findAll(pageable);
        return skills.map(skillMapper::mapSkillToDTO);
    }

    @Override
    public void deleteSkill(UUID skillId) {
        Skill existingSkill = skillRepository.findById(skillId)
                .orElseThrow(() -> new EntityNotFoundException("Skill not found with ID: " + skillId));

        skillRepository.delete(existingSkill);
    }

}
