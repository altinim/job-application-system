package com.example.careerify.repository;

import com.example.careerify.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
public interface EducationRepository extends JpaRepository<Education, UUID> {

    List<Education> findAllByUserId(UUID userId);

}
