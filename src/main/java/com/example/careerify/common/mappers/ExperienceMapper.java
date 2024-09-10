package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.ExperienceDTO;
import com.example.careerify.model.Experience;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ExperienceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<Experience, ExperienceDTO>() {
            @Override
            protected void configure() {
                map().setUser(source.getUser().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<ExperienceDTO, Experience>() {
            @Override
            protected void configure() {
                skip(destination.getUser());
            }
        });
    }

    public ExperienceDTO mapExperienceToDTO(Experience experience) {
        return modelMapper.map(experience, ExperienceDTO.class);
    }

    public Experience mapDTOToExperience(ExperienceDTO experienceDTO) {
        return modelMapper.map(experienceDTO, Experience.class);
    }

    public void updateExperienceFromDTO(ExperienceDTO updateDTO, Experience experience) {
        modelMapper.map(updateDTO, experience);
    }
}
