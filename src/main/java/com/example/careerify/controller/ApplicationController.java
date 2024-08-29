package com.example.careerify.controller;

import com.example.careerify.common.dto.ApplicationRequestDTO;
import com.example.careerify.common.dto.ApplicationResponseDTO;
import com.example.careerify.common.enums.ApplicationStatus;
import com.example.careerify.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/apply/{jobListingId}")
    public ResponseEntity<ApplicationResponseDTO> applyForJob(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long jobListingId) {
        ApplicationResponseDTO responseDTO = applicationService.applyForAJobListing(authorizationHeader, jobListingId);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long applicationId) {
        ApplicationResponseDTO application = applicationService.getApplicationById(applicationId);
        return ResponseEntity.ok(application);
    }

    @GetMapping
    public ResponseEntity<Page<ApplicationResponseDTO>> getAllApplications(Pageable pageable) {
        Page<ApplicationResponseDTO> applicationsPage = applicationService.getAllApplicationsPaged(pageable);
        return ResponseEntity.ok(applicationsPage);

    }
    @PutMapping("/{applicationId}")
    public ResponseEntity<Void> updateApplication(
            @PathVariable Long applicationId,
            @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        applicationService.updateApplication(applicationId, applicationRequestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}/applicant/{applicantId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByStatusAndApplicant(
            @PathVariable ApplicationStatus status, @PathVariable UUID applicantId) {
        List<ApplicationResponseDTO> applications = applicationService.getApplicationsByStatusAndApplicant(status, applicantId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/jobListing/{jobListingId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJobListing(
            @PathVariable Long jobListingId) {
        List<ApplicationResponseDTO> applications = applicationService.getApplicationsByJobListing(jobListingId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByStatus(
            @PathVariable ApplicationStatus status) {
        List<ApplicationResponseDTO> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/count/status/{status}/jobListing/{jobListingId}")
    public ResponseEntity<Long> countApplicationsByStatusAndJobListing(
            @PathVariable ApplicationStatus status, @PathVariable Long jobListingId) {
        long count = applicationService.countApplicationsByStatusAndJobListing(status, jobListingId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/count/jobListing/{jobListingId}/applicant/{applicantId}")
    public ResponseEntity<Long> countApplicationsByJobListingAndApplicant(
            @PathVariable Long jobListingId, @PathVariable UUID applicantId) {
        long count = applicationService.countApplicationsByJobListingAndApplicant(jobListingId, applicantId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/jobListing/{jobListingId}/applicant/{applicantId}")
    public ResponseEntity<List<ApplicationResponseDTO>> getApplicationsByJobListingAndApplicant(
            @PathVariable Long jobListingId, @PathVariable UUID applicantId) {
        List<ApplicationResponseDTO> applications = applicationService
                .getApplicationsByJobListingAndApplicant(jobListingId, applicantId);
        return ResponseEntity.ok(applications);
    }

    @PatchMapping("/{applicationId}/updateStatus/{newStatus}")
    public ResponseEntity<Void> updateApplicationStatus(
            @PathVariable Long applicationId, @PathVariable ApplicationStatus newStatus) {
        applicationService.updateApplicationStatus(applicationId, newStatus);
        return ResponseEntity.ok().build();
    }
}
