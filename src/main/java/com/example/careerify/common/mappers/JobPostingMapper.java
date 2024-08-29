package com.example.careerify.common.mappers;

import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.model.JobPosting;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobPostingMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public JobPostingMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
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
