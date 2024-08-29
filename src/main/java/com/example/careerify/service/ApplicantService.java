package com.example.careerify.service;

import com.example.careerify.common.dto.ApplicantRequestDTO;
import com.example.careerify.common.dto.ApplicantResponseDTO;
import com.example.careerify.model.Applicant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface ApplicantService {

    ApplicantResponseDTO createApplicant(ApplicantRequestDTO applicantRequestDTO);
    ApplicantResponseDTO getApplicantById(UUID id);
    UserDetailsService userDetailsService();
    Applicant save(Applicant newUser);
    List<ApplicantResponseDTO> getAllApplicants();
    void updateApplicant(UUID id, ApplicantRequestDTO updateDTO);
    void deleteApplicant(UUID applicantId);

}
