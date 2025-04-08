package org.example.mobile.service;

import org.example.mobile.dto.response.DoctorResponse;

import java.util.List;

public interface DoctorService {
    List<String> getAllSpecialties();
    
    List<DoctorResponse> getDoctorsBySpecialtyId(Long specialtyId);
} 