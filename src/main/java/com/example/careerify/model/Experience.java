package com.example.careerify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@Data
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;
    @NonNull
    private String title;

    @NonNull
    private String company;

    @NonNull
    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isPresent;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user", nullable = false)
    private User user;

}
