package com.example.careerify.common.dto;

import com.example.careerify.common.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
}
