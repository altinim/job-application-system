package com.example.careerify.service;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.SkillDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
public interface SkillService {
    SkillDTO createSkill(SkillDTO skillDTO);
    SkillDTO getSkillById (UUID id);
    Page<SkillDTO> getAllSkills (Pageable pageable);
    void deleteSkill(UUID skillId);
    List<SkillDTO> getSkillsByUserId(UUID userId);
}
