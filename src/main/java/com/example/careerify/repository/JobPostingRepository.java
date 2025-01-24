package com.example.careerify.repository;

import com.example.careerify.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobPostingRepository  extends JpaRepository<JobPosting,Long> {
    Page<JobPosting> findByUserId(UUID employerId, Pageable pageable);

    @Query("SELECT j FROM JobPosting j WHERE "
            + "(COALESCE(:title, '') = '' OR LOWER(j.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND "
            + "(COALESCE(:salary, -1) = -1 OR j.salary >= :salary) AND "
            + "(COALESCE(:postDate, null) IS NULL OR j.postDate >= :postDate) AND "
            + "(COALESCE(:category, '') = '' OR LOWER(j.category) = LOWER(:category))")
    Page<JobPosting> findJobPostings(
            @Param("title") String title,
            @Param("salary") Float salary,
            @Param("postDate") LocalDate postDate,
            @Param("category") String category,
            Pageable pageable
    );

    Page<JobPosting> findAllByOrderByPostDateDesc(Pageable pageable);


}
