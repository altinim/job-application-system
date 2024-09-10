package com.example.careerify.common.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Getter
@Setter
public class SkillDTO {
    private UUID id;

    private UUID user;
    @NotBlank(message = "Name cannot be blank")
    private String name;
}
