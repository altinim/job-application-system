package com.example.careerify.common.dto;

import com.example.careerify.common.enums.ApplicationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequestDTO {

    @NotNull(message = "Job Listing ID cannot be null")
    private Long jobListingId;

    @NotNull(message = "Applicant ID cannot be null")
    private UUID applicantId;



}
