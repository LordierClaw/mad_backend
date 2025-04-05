package org.example.mobile.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "doctors")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specialty", nullable = false, length = 250)
    private String specialty;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
