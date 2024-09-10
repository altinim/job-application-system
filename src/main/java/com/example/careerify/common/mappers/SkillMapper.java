package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.common.dto.SkillDTO;
import com.example.careerify.model.Experience;
import com.example.careerify.model.Skill;
import org.mapstruct.InheritConfiguration;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
public class SkillMapper {

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    public SkillMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<Skill, SkillDTO>() {
            @Override
            protected void configure() {
                map().setUser(source.getUser().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<SkillDTO, Skill>() {
            @Override
            protected void configure() {
                skip(destination.getUser());
            }
        });
    }


    @InheritConfiguration
    public SkillDTO mapSkillToDTO(Skill skill) {
        return modelMapper.map(skill, SkillDTO.class);
    }

    @InheritConfiguration
    public  Skill mapDTOToSkill(SkillDTO skillDTO) {
        return modelMapper.map(skillDTO, Skill.class);
    }

    public void updateSkillFromDTO(SkillDTO updateDTO, Skill skill) {
        modelMapper.map(updateDTO, skill);
    }


}
