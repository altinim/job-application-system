package com.example.careerify.service;

import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.common.mappers.JobPostingMapper;
import com.example.careerify.model.JobPosting;
import com.example.careerify.repository.JobPostingRepository;
import com.example.careerify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobPostingServiceImpl implements JobPostingService {

    private final JobPostingRepository jobPostingRepository;
    private final JobPostingMapper jobPostingMapper;
    private final JwtService jwtService;

    public JobPostingServiceImpl(JobPostingRepository jobPostingRepository, JobPostingMapper jobPostingMapper , JwtService jwtService){
        this.jobPostingRepository = jobPostingRepository;
        this.jobPostingMapper = jobPostingMapper;
        this.jwtService = jwtService;
    }
    @Override
    public Page<JobPostingResponseDTO> getAllJobPostings(Pageable pageable) {
        Page<JobPosting> jobPostings = jobPostingRepository.findAll(pageable);
        return jobPostings.map(jobPostingMapper::mapJobPostingToDTO);
    }
    @Override
    public JobPostingResponseDTO createJobPosting(JobPostingRequestDTO requestDTO) {
        UUID currentUserId = jwtService.getCurrentUserId();

        JobPosting jobPosting = jobPostingMapper.mapDTOToJobPosting(requestDTO);
        jobPosting.setEmployerId(currentUserId);

        JobPosting savedJobPosting = jobPostingRepository.save(jobPosting);

        return jobPostingMapper.mapJobPostingToDTO(savedJobPosting);
    }

    @Override
    public JobPostingResponseDTO getJobPostingById(Long jobPostingId) {
        Optional<JobPosting> jobPostingOptional = jobPostingRepository.findById(jobPostingId);
        if (jobPostingOptional.isPresent()) {
            return jobPostingMapper.mapJobPostingToDTO(jobPostingOptional.get());
        } else {
            throw new RuntimeException("Job Posting not found with ID: " + jobPostingId);
        }
    }

    @Override
    public void updateJobPosting(Long jobPostingId, JobPostingResponseDTO jobPostingResponseDTO) {
        Optional<JobPosting> jobPostingOptional = jobPostingRepository.findById(jobPostingId);
        if (jobPostingOptional.isPresent()) {
            JobPosting existingJobPosting = jobPostingOptional.get();
            jobPostingMapper.updateJobPostingFromDTO(jobPostingResponseDTO, existingJobPosting);
            jobPostingRepository.save(existingJobPosting);
        } else {
            throw new RuntimeException("Job Posting not found with ID: " + jobPostingId);
        }
    }

    @Override
    public void deleteJobPosting(Long jobPostingId) {
        Optional<JobPosting> jobPostingOptional = jobPostingRepository.findById(jobPostingId);
        jobPostingOptional.ifPresent(jobPostingRepository::delete);
    }


    @Override
    public List<JobPostingResponseDTO> searchJobPostings(
            String title, Float salary, LocalDate postDate, String category, int page, int size
    ) {
        Page<JobPosting> jobPostingPage = jobPostingRepository.findJobPostings(
                title, salary, postDate, category, PageRequest.of(page, size)
        );

        List<JobPosting> jobPostings = jobPostingPage.getContent();

        return jobPostings.stream()
                .map(jobPostingMapper::mapJobPostingToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<JobPostingResponseDTO> getJobPostingByCurrentEmployer(Pageable pageable) {
        UUID currentUserId = jwtService.getCurrentUserId();

        Page<JobPosting> jobPostings = jobPostingRepository.findByEmployerId(currentUserId, pageable);
        return jobPostings.map(jobPostingMapper::mapJobPostingToDTO);
    }

}
