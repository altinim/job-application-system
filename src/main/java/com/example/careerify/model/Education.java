package com.example.careerify.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    private String fieldOfStudy;

    private String school;

    private Date endDate;

    private Date startDate;

    private String location;

    private String grade;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user", nullable = false)
    private User user;
}
