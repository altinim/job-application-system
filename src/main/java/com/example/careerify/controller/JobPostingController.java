package com.example.careerify.controller;

import com.example.careerify.common.dto.JobPostingRequestDTO;
import com.example.careerify.common.dto.JobPostingResponseDTO;
import com.example.careerify.service.JobPostingService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/job-postings")
public class JobPostingController {

    private final JobPostingService jobPostingService;

    public JobPostingController(JobPostingService jobPostingService) {
        this.jobPostingService = jobPostingService;
    }

    @GetMapping
    public ResponseEntity<Page<JobPostingResponseDTO>> getAllJobPostings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<JobPostingResponseDTO> jobPostings = jobPostingService.getAllJobPostings(pageable);
        return new ResponseEntity<>(jobPostings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobPostingResponseDTO> createJobPosting(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody JobPostingRequestDTO jobPostingRequestDTO) {
        JobPostingResponseDTO responseDTO = jobPostingService.createJobPosting(jobPostingRequestDTO, authorizationHeader);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("employer-job-listings")
    public ResponseEntity<Page<JobPostingResponseDTO>> getJobPostingByCurrentEmployer(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<JobPostingResponseDTO> jobPostings = jobPostingService.getJobPostingByCurrentEmployer(pageable, authorizationHeader);
        return new ResponseEntity<>(jobPostings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobPostingResponseDTO> getJobPostingById(@PathVariable Long id) {
        JobPostingResponseDTO jobPostingResponseDTO = jobPostingService.getJobPostingById(id);
        return new ResponseEntity<>(jobPostingResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateJobPosting(@PathVariable Long id, @RequestBody JobPostingResponseDTO updateDTO) {
        jobPostingService.updateJobPosting(id, updateDTO);
        return new ResponseEntity<>("Job Posting updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteJobPosting(@PathVariable Long id) {
        jobPostingService.deleteJobPosting(id);
        return new ResponseEntity<>("Job Posting deleted successfully", HttpStatus.NO_CONTENT);
    }
    @GetMapping("/search")
    public ResponseEntity<List<JobPostingResponseDTO>> searchJobPostings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Float salary,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate postDate,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<JobPostingResponseDTO> jobPostings = jobPostingService.searchJobPostings(
                title, salary, postDate, category, page, size
        );
        return ResponseEntity.ok(jobPostings);
    }
}
