package com.example.careerify.common.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class ExperienceDTO {

    UUID id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String company;

    @NotNull(message = "Start date cannot be null")
    private LocalDate startDate;

    private UUID user;

    @NotNull(message = "End date cannot be null")
    private LocalDate endDate;
}
