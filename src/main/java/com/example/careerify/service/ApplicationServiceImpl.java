package com.example.careerify.service;

import com.example.careerify.common.dto.ApplicationRequestDTO;
import com.example.careerify.common.dto.ApplicationResponseDTO;
import com.example.careerify.common.enums.ApplicationStatus;
import com.example.careerify.common.jwt.JwtService;
import com.example.careerify.common.mappers.ApplicationMapper;
import com.example.careerify.model.Applicant;
import com.example.careerify.model.Application;
import com.example.careerify.model.JobPosting;
import com.example.careerify.repository.ApplicantRepository;
import com.example.careerify.repository.ApplicationRepository;
import com.example.careerify.repository.JobPostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final JobPostingRepository jobPostingRepository;
    private final ApplicationMapper applicationMapper;

    private final JwtService jwtService;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicantRepository applicantRepository,
                                  JobPostingRepository jobPostingRepository,
                                  ApplicationMapper applicationMapper,
            JwtService jwtService){
        this.applicationRepository = applicationRepository;
        this.applicantRepository = applicantRepository;
        this.jobPostingRepository = jobPostingRepository;
        this.applicationMapper = applicationMapper;
        this.jwtService = jwtService;
    }
    @Override
    public ApplicationResponseDTO applyForAJobListing(String authorizationHeader, Long jobListingId) {
        // Extract applicant ID from the token
        UUID applicantId = extractUserIdFromToken(authorizationHeader);

        Applicant applicant = getApplicantById(applicantId);
        JobPosting jobPosting = getJobPostingById(jobListingId);

        Application application = new Application();
        application.setApplicant(applicant);
        application.setJobListing(jobPosting);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.mapApplicationToResponseDTO(savedApplication);
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
    public List<ApplicationResponseDTO> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
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
    public List<ApplicationResponseDTO> getApplicationsByStatusAndApplicant(ApplicationStatus status, UUID applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        List<Application> applications = applicationRepository.findByStatusAndApplicant(status, applicant);
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
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

    @Override
    public long countApplicationsByJobListingAndApplicant(Long jobListingId, UUID applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        JobPosting jobListing = getJobPostingById(jobListingId);
        return applicationRepository.countByJobListingAndApplicant(jobListing, applicant);
    }
    @Override
    public List<ApplicationResponseDTO> getApplicationsByJobListingAndApplicant(Long jobListingId, UUID applicantId) {
        Applicant applicant = getApplicantById(applicantId);
        JobPosting jobListing = getJobPostingById(jobListingId);
        List<Application> applications = applicationRepository.findByJobListingAndApplicant(jobListing, applicant);
        return applicationMapper.mapApplicationsToResponseDTOs(applications);
    }

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

    //helper methods

    private Applicant getApplicantById(UUID applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> new RuntimeException("Applicant not found with ID: " + applicantId));
    }
    private JobPosting getJobPostingById(Long jobPostingId) {
        return jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new RuntimeException("Job Posting not found with ID: " + jobPostingId));
    }

}
