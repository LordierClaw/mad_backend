package org.example.mobile.repository;

import org.example.mobile.entity.DoctorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorInfoRepository extends JpaRepository<DoctorInfo, Long> {
    List<DoctorInfo> findByDoctorId(Long doctorId);
} 