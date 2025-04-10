package org.example.mobile.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "medicine_ingredients")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MedicineIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 250)
    private String name;
    
    @Column(name = "amount", length = 250)
    private String amount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
} 