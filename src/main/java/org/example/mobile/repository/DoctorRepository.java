package org.example.mobile.repository;

import org.example.mobile.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT DISTINCT d.specialty FROM Doctor d ORDER BY d.specialty")
    List<String> findAllSpecialties();
    
    @Query("SELECT d FROM Doctor d WHERE d.specialty = (SELECT doc.specialty FROM Doctor doc WHERE doc.id = :specialtyId)")
    List<Doctor> findDoctorsBySpecialtyId(@Param("specialtyId") Long specialtyId);
} 