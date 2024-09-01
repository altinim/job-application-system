package com.example.careerify.common.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private List<ApplicationResponseDTO> applications;
}
