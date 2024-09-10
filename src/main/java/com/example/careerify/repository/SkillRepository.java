package com.example.careerify.repository;
import com.example.careerify.model.Education;
import com.example.careerify.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SkillRepository extends JpaRepository<Skill, UUID>   {

    List<Skill> findAllByUserId(UUID userId);


}
