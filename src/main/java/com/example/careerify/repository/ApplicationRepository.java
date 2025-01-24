package com.example.careerify.repository;

import com.example.careerify.common.enums.ApplicationStatus;
import com.example.careerify.model.Application;
import com.example.careerify.model.JobPosting;
import com.example.careerify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByJobListing(JobPosting jobListing);


    List<Application> findByStatus(ApplicationStatus status);

    long countByStatusAndJobListing(ApplicationStatus status, JobPosting jobListing);


}
