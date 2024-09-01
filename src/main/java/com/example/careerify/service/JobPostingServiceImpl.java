package com.example.careerify.service;

import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.common.mappers.JobPostingMapper;
import com.example.careerify.model.Applicant;
import com.example.careerify.model.JobPosting;
import com.example.careerify.repository.ApplicantRepository;
import com.example.careerify.repository.JobPostingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
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
    private final ApplicantRepository applicantRepository;

    public JobPostingServiceImpl(JobPostingRepository jobPostingRepository, JobPostingMapper jobPostingMapper , JwtService jwtService, ApplicantRepository applicantRepository){
        this.jobPostingRepository = jobPostingRepository;
        this.jobPostingMapper = jobPostingMapper;
        this.jwtService = jwtService;
        this.applicantRepository = applicantRepository;
    }
    @Override
    public Page<JobPostingResponseDTO> getAllJobPostings(Pageable pageable) {
        Page<JobPosting> jobPostings = jobPostingRepository.findAll(pageable);
        return jobPostings.map(jobPostingMapper::mapJobPostingToDTO);
    }
    @Override
    public JobPostingResponseDTO createJobPosting(JobPostingRequestDTO requestDTO, String authorizationHeader) {
        UUID currentUserId = extractUserIdFromToken(authorizationHeader);

        Applicant employeer = applicantRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Employeer not found"));

        JobPosting jobPosting = new JobPosting();
        jobPosting.setTitle(requestDTO.getTitle());
        jobPosting.setDescription(requestDTO.getDescription());
        jobPosting.setSalary(requestDTO.getSalary());
        jobPosting.setPostDate(requestDTO.getPostDate());
        jobPosting.setEmployeer(employeer);
        jobPosting.setEndDate(requestDTO.getEndDate());
        jobPosting.setLocation(requestDTO.getLocation());
        jobPosting.setCategory(requestDTO.getCategory());
        jobPosting.setOpenPositions(requestDTO.getOpenPositions());

        // Save the JobPosting entity
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
    private UUID extractUserIdFromToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }
}
