package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.SkillDTO;
import com.example.careerify.model.Skill;
import org.mapstruct.InheritConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class SkillMapper {
    @Autowired
    private ModelMapper modelMapper ;

    @InheritConfiguration
    public SkillDTO mapSkillToDTO(Skill skill) {
        return modelMapper.map(skill, SkillDTO.class);
    }

    @InheritConfiguration
    public  Skill mapDTOToSkill(SkillDTO skillDTO) {
        return modelMapper.map(skillDTO, Skill.class);
    }

}
