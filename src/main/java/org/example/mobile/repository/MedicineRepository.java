package org.example.mobile.repository;

import org.example.mobile.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {
    
    @Query("SELECT DISTINCT m FROM Medicine m LEFT JOIN FETCH m.ingredients")
    List<Medicine> findAllWithIngredients();
    
    @Query("SELECT DISTINCT m FROM Medicine m LEFT JOIN FETCH m.ingredients WHERE m.id = :id")
    Optional<Medicine> findByIdWithIngredients(Long id);
} 