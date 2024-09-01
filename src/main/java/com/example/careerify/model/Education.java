package com.example.careerify.model;
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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Education(UUID id, String fieldOfStudy, String school, Date endDate, Date startDate, String location, String grade) {
        this.id = id;
        this.fieldOfStudy = fieldOfStudy;
        this.school = school;
        this.endDate = endDate;
        this.startDate = startDate;
        this.location = location;
        this.grade = grade;
    }
}
