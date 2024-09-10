package com.example.careerify.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@Data
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;
}
