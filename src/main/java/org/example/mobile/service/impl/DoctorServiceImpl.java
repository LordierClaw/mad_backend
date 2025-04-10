package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.dto.response.DoctorResponse;
import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.DoctorInfo;
import org.example.mobile.entity.User;
import org.example.mobile.repository.DoctorInfoRepository;
import org.example.mobile.repository.DoctorRepository;
import org.example.mobile.repository.UserRepository;
import org.example.mobile.service.DoctorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorInfoRepository doctorInfoRepository;
    private final UserRepository userRepository;

    @Override
    public List<String> getAllSpecialties() {
        return doctorRepository.findAllSpecialties();
    }
    
    @Override
    public List<DoctorResponse> getDoctorsBySpecialtyId(Long specialtyId) {
        List<Doctor> doctors = doctorRepository.findDoctorsBySpecialtyId(specialtyId);

        List<Long> doctorIds = doctors.stream().map(Doctor::getId).toList();
        List<User> users = userRepository.findAllById(doctorIds);
        
        List<DoctorInfo> doctorInfos = doctors.stream()
                .map(doctor -> doctorInfoRepository.findByDoctorId(doctor.getId()))
                .flatMap(List::stream)
                .toList();
        return doctors.stream().map(doctor -> {
            DoctorResponse doctorResponse = new DoctorResponse();
            BeanUtils.copyProperties(doctor, doctorResponse);
            DoctorInfo info = doctorInfos.stream()
                    .filter(di -> di.getDoctorId().equals(doctor.getId()))
                    .findFirst()
                    .orElse(new DoctorInfo());
            BeanUtils.copyProperties(info, doctorResponse);
            User user = users.stream()
                    .filter(u -> u.getId().equals(doctor.getId()))
                    .findFirst()
                    .orElse(new User());
            BeanUtils.copyProperties(user, doctorResponse);
            return doctorResponse;
        }).toList();
    }
} 