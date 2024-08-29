package com.example.careerify.service;

import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface JobPostingService {

    JobPostingResponseDTO createJobPosting(JobPostingRequestDTO jobPostingRequestDTO, String token);

    JobPostingResponseDTO getJobPostingById(Long jobPostingId);

    void updateJobPosting(Long jobPostingId, JobPostingResponseDTO jobPostingResponseDTO);

    void deleteJobPosting(Long jobPostingId);

    Page<JobPostingResponseDTO> getAllJobPostings(Pageable pageable);


    List<JobPostingResponseDTO> getJobPostingsByPostDateAfter(LocalDate postDate);

    List<JobPostingResponseDTO> getJobPostingsBySalaryGreaterThan(float salary);

    List<JobPostingResponseDTO> getJobPostingsByTitle(String keyword);

}
