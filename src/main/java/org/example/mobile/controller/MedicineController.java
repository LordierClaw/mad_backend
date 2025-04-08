package org.example.mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.entity.Medicine;
import org.example.mobile.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    
    @PostMapping("/create")
    public ResponseEntity<Result> createMedicine(@RequestBody @Valid CreateMedicineRequest request) {
        Medicine medicine = medicineService.createMedicine(request);
        return ResponseEntity.ok(new Result("Thêm thuốc thành công", medicine));
    }
}
