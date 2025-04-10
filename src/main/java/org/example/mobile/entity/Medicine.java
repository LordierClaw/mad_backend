package org.example.mobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table(name = "medicines")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "img_url", length = 500)
    private String imgUrl;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "use", columnDefinition = "TEXT")
    private String use;

    @Column(name = "usage", columnDefinition = "TEXT")
    private String usage;

    @Column(name = "precaution", columnDefinition = "TEXT")
    private String precaution;
    
    @OneToMany(mappedBy = "medicine", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<MedicineIngredient> ingredients = new ArrayList<>();
}
