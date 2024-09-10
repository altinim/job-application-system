package com.example.careerify.repository;

import com.example.careerify.model.Education;
import com.example.careerify.model.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, UUID>  {

    List<Experience> findAllByUserId(UUID userId);

}
