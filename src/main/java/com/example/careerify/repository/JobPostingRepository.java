package com.example.careerify.repository;

import com.example.careerify.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface JobPostingRepository  extends JpaRepository<JobPosting,Long> {
    List<JobPosting> findByTitleContaining(String keyword);
    List<JobPosting> findBySalaryGreaterThan(float salary);
    List<JobPosting> findByPostDateAfter(LocalDate postDate);

}
