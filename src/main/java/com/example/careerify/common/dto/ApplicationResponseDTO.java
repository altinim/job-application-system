package com.example.careerify.common.dto;

import com.example.careerify.common.enums.ApplicationStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponseDTO {
    private Long id;
    private Long jobListingId;
    private UUID applicantId;
    private ApplicationStatus status;
    private String firstName;
    private String lastName;

}
