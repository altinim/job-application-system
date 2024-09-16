package com.example.careerify.service;

import com.example.careerify.common.dto.ApplicationRequestDTO;
import com.example.careerify.common.dto.ApplicationResponseDTO;
import com.example.careerify.common.enums.ApplicationStatus;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.common.mappers.ApplicationMapper;
import com.example.careerify.model.User;
import com.example.careerify.model.Application;
import com.example.careerify.model.JobPosting;
import com.example.careerify.repository.ApplicationRepository;
import com.example.careerify.repository.JobPostingRepository;
import com.example.careerify.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationMapper applicationMapper;

    private final JwtService jwtService;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  UserRepository userRepository,
                                  JobPostingRepository jobPostingRepository,
                                  ApplicationMapper applicationMapper,
            JwtService jwtService){
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.applicationMapper = applicationMapper;
        this.jwtService = jwtService;
    }
    @Override
    public ApplicationResponseDTO applyForAJobListing(String authorizationHeader, Long jobListingId) {
        UUID userId = extractUserIdFromToken(authorizationHeader);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        JobPosting jobPosting = getJobPostingById(jobListingId);

        Application application = new Application();
        application.setJobListing(jobPosting);
        application.setApplicant(user);
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.mapApplicationToResponseDTO(savedApplication);
    }
    @Override
    public List<ApplicationResponseDTO> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
    }

    private UUID extractUserIdFromToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtService.extractUserId(token);
    }

    @Override
    public ApplicationResponseDTO getApplicationById(Long applicationId) {
        Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
        return applicationOptional.map(applicationMapper::mapApplicationToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + applicationId));
    }

    @Override
    public Page<ApplicationResponseDTO> getAllApplicationsPaged(Pageable pageable) {
        Page<Application> applicationPage = applicationRepository.findAll(pageable);
        return applicationPage.map(applicationMapper::mapApplicationToResponseDTO);
    }
    @Override
    public void updateApplication(Long applicationId, ApplicationRequestDTO applicationRequestDTO) {
        Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
        if (applicationOptional.isPresent()) {
            Application existingApplication = applicationOptional.get();
            applicationMapper.mapRequestDTOToApplication(applicationRequestDTO, existingApplication);
            applicationRepository.save(existingApplication);
        } else {
            throw new RuntimeException("Application not found with ID: " + applicationId);
        }
    }
    @Override
    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobListing(Long jobListingId) {
        JobPosting jobListing = getJobPostingById(jobListingId);
        List<Application> applications = applicationRepository.findByJobListing(jobListing);
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
    }
    @Override
    public List<ApplicationResponseDTO> getApplicationsByStatus(ApplicationStatus status) {
        List<Application> applications = applicationRepository.findByStatus(status);
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
    }

    @Override
    public long countApplicationsByStatusAndJobListing(ApplicationStatus status, Long jobListingId) {
        JobPosting jobListing = getJobPostingById(jobListingId);
        return applicationRepository.countByStatusAndJobListing(status, jobListing);
    }

    //helper methods
    @Override
    public void updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        Optional<Application> applicationOptional = applicationRepository.findById(applicationId);

        if (applicationOptional.isPresent()) {
            Application application = applicationOptional.get();
            application.setStatus(newStatus);
            applicationRepository.save(application);
        } else {
            throw new RuntimeException("Application not found with ID: " + applicationId);
        }
    }
    private User getUserById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
    private JobPosting getJobPostingById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job Posting not found with ID: " + jobPostingId));
    }

}
