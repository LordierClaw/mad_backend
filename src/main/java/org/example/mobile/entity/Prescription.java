package org.example.mobile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "prescriptions")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "prescription_name", nullable = false, length = 250)
    private String prescriptionName;

    @Column(name = "prescription_date")
    private LocalDateTime prescriptionDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "status", nullable = false, length = 10)
    private String status;
}
