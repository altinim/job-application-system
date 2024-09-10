package com.example.careerify.common.dto;

import com.example.careerify.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Data
@Getter
@Setter
public class EducationDTO {

    private UUID id;

    private String fieldOfStudy;

    private String school;

    private Date endDate;

    private Date startDate;

    private UUID user;

    private String location;

    private String grade;
}
