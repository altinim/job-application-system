package com.example.careerify.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class JobPostingRequestDTO {
    private String title;
    private String description;
    private float salary;
    private UUID employerId;
    private LocalDate postDate;
    private LocalDate endDate;
    private String location;
    private String category;
    private int openPositions;
}
