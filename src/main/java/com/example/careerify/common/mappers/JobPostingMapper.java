package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.ApplicationResponseDTO;
import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.model.Application;
import com.example.careerify.model.JobPosting;
import com.example.careerify.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.careerify.*;

@Component
public class JobPostingMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public JobPostingMapper(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        configureMappings();
    }

    private void configureMappings() {
        modelMapper.addMappings(new PropertyMap<JobPosting, JobPostingResponseDTO>() {
            @Override
            protected void configure() {
                map().setUserId(source.getUser().getId()); // Map User ID to userId
                // Other mappings if necessary
            }
        });
        modelMapper.addMappings(new PropertyMap<JobPostingRequestDTO, JobPosting>() {
            @Override
            protected void configure() {
                skip(destination.getUser()); // Skip User mapping
            }
        });
    }

    public JobPostingResponseDTO mapJobPostingToDTO(JobPosting jobPosting) {
        JobPostingResponseDTO dto = modelMapper.map(jobPosting, JobPostingResponseDTO.class);

        String userName = userService.getUserNameById(jobPosting.getUser().getId());
        dto.setCompanyName(userName);

        return dto;
    }

    public JobPosting mapDTOToJobPosting(JobPostingRequestDTO jobPostingRequestDTO) {
        return modelMapper.map(jobPostingRequestDTO, JobPosting.class);
    }

    public void updateJobPostingFromDTO(JobPostingResponseDTO jobPostingResponseDTO, JobPosting jobPosting) {
        modelMapper.map(jobPostingResponseDTO, jobPosting);
    }
}