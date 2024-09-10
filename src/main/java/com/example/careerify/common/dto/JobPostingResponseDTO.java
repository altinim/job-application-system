package com.example.careerify.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class JobPostingResponseDTO {
    private Long id;
    private String title;
    private String description;
    private float salary;
    private UUID user;
    private List<ApplicationResponseDTO> applications;
    private LocalDate postDate;
    private LocalDate endDate;
    private String location;
    private String category;
    private int openPositions;
}
