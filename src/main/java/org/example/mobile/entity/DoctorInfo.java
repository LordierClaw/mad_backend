package org.example.mobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table(name = "doctors_info")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DoctorInfo {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;
    
    @Column(name = "title", length = 250)
    private String title;
    
    @Column(name = "description", columnDefinition = "text")
    private String description;
    
    @Column(name = "\"order_num\"")
    private Integer order;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Doctor doctor;
} 