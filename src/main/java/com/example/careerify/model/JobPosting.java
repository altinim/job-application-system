package com.example.careerify.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    private String title;

    private String description;

    private float salary;

    @ManyToOne
    @JoinColumn(name = "employeer_id", nullable = false)
    private Applicant employeer;

    @OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    private LocalDate postDate;

    private LocalDate endDate;

    private String location;

    private String category;

    private int openPositions;
}
