package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.ApplicationResponseDTO;
import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.model.Application;
import com.example.careerify.model.JobPosting;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.careerify.*;
@Component
public class JobPostingMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public JobPostingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMappings();
    }


    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<JobPosting, JobPostingResponseDTO>() {
            @Override
            protected void configure() {
                map().setUser(source.getUser().getId());
            }
        });
        modelMapper.addMappings(new PropertyMap<JobPostingRequestDTO, JobPosting>() {
            @Override
            protected void configure() {
                skip(destination.getUser());
            }
        });
    }


    public JobPostingResponseDTO mapJobPostingToDTO(JobPosting jobPosting) {
        return modelMapper.map(jobPosting, JobPostingResponseDTO.class);
    }

    public JobPosting mapDTOToJobPosting(JobPostingRequestDTO jobPostingRequestDTO) {
        return modelMapper.map(jobPostingRequestDTO, JobPosting.class);
    }
    public void updateJobPostingFromDTO(JobPostingResponseDTO jobPostingResponseDTO, JobPosting jobPosting) {
        modelMapper.map(jobPostingResponseDTO, jobPosting);
    }


}
