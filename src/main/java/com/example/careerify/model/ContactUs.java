package com.example.careerify.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contactUsMessages")
public class ContactUs {

    @Id
    private String id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Subject is mandatory")
    @Size(max = 150, message = "Subject cannot exceed 150 characters")
    private String subject;

    @NotBlank(message = "Message is mandatory")
    @Size(max = 1000, message = "Message cannot exceed 1000 characters")
    private String message;
}
