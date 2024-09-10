package com.example.careerify.common.mappers;

import com.example.careerify.model.Education;
import com.example.careerify.common.dto.EducationDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EducationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<Education, EducationDTO>() {
            @Override
            protected void configure() {
                map().setUser(source.getUser().getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<EducationDTO, Education>() {
            @Override
            protected void configure() {
                skip(destination.getUser());
            }
        });
    }

    public EducationDTO mapEducationToDTO(Education education) {
        return modelMapper.map(education, EducationDTO.class);
    }

    public Education mapDTOToEducation(EducationDTO educationDTO) {
        return modelMapper.map(educationDTO, Education.class);
    }

    public void updateEducationFromDTO(EducationDTO updateDTO, Education education) {
        modelMapper.map(updateDTO, education);
    }
}
