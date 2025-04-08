package org.example.mobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "specialty", length = 250)
    private String specialty;
    
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;
} 