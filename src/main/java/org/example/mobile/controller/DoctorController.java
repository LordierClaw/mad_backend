package org.example.mobile.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.example.mobile.dto.response.DoctorResponse;
import org.example.mobile.dto.response.SpecialtyResponse;
import org.example.mobile.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;
    
    @GetMapping("/specialties")
    public ResponseEntity<Result> getAllSpecialties() {
        List<String> specialties = doctorService.getAllSpecialties();
        SpecialtyResponse response = new SpecialtyResponse(specialties);
        return ResponseEntity.ok(new Result("Lấy danh sách chuyên môn thành công", response));
    }

    @GetMapping("/specialty-id/{specialtyId}")
    public ResponseEntity<Result> getDoctorsBySpecialtyId(@PathVariable Long specialtyId) {
        List<DoctorResponse> responses = doctorService.getDoctorsBySpecialtyId(specialtyId);
        return ResponseEntity.ok(new Result("Lấy danh sách bác sĩ theo ID chuyên khoa thành công", responses));
    }
} 