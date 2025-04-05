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
}
