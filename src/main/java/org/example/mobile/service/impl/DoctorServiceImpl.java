package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.dto.response.DoctorResponse;
import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.DoctorInfo;
import org.example.mobile.repository.DoctorInfoRepository;
import org.example.mobile.repository.DoctorRepository;
import org.example.mobile.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorInfoRepository doctorInfoRepository;

    @Override
    public List<String> getAllSpecialties() {
        return doctorRepository.findAllSpecialties();
    }
    
    @Override
    public List<DoctorResponse> getDoctorsBySpecialtyId(Long specialtyId) {
        List<Doctor> doctors = doctorRepository.findDoctorsBySpecialtyId(specialtyId);
        
        List<DoctorInfo> doctorInfos = doctors.stream()
                .map(doctor -> doctorInfoRepository.findByDoctorId(doctor.getId()))
                .flatMap(List::stream)
                .toList();
        
        return DoctorResponse.fromEntities(doctors, doctorInfos);
    }
} 