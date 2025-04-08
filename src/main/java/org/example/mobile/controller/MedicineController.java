package org.example.mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.dto.request.DeleteMedicinesRequest;
import org.example.mobile.dto.request.UpdateMedicineRequest;
import org.example.mobile.dto.response.MedicineResponse;
import org.example.mobile.entity.Medicine;
import org.example.mobile.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    
    @PostMapping("/create")
    public ResponseEntity<Result> createMedicine(@RequestBody @Valid CreateMedicineRequest request) {
        Medicine medicine = medicineService.createMedicine(request);
        MedicineResponse response = MedicineResponse.fromEntity(medicine);
        return ResponseEntity.ok(new Result("Thêm thuốc thành công", response));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Result> updateMedicine(
            @PathVariable Long id, 
            @RequestBody @Valid UpdateMedicineRequest request) {
        Medicine medicine = medicineService.updateMedicine(id, request);
        MedicineResponse response = MedicineResponse.fromEntity(medicine);
        return ResponseEntity.ok(new Result("Cập nhật thuốc thành công", response));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return ResponseEntity.ok(new Result("Xóa thuốc thành công"));
    }
    
    @PostMapping("/delete-multiple")
    public ResponseEntity<Result> deleteMedicines(@RequestBody @Valid DeleteMedicinesRequest request) {
        medicineService.deleteMedicines(request.getIds());
        return ResponseEntity.ok(new Result("Xóa các thuốc thành công"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Result> getMedicineById(@PathVariable Long id) {
        Medicine medicine = medicineService.getMedicineById(id);
        MedicineResponse response = MedicineResponse.fromEntity(medicine);
        return ResponseEntity.ok(new Result("Lấy thông tin thuốc thành công", response));
    }
    
    @GetMapping
    public ResponseEntity<Result> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        List<MedicineResponse> responses = MedicineResponse.fromEntities(medicines);
        return ResponseEntity.ok(new Result("Lấy danh sách thuốc thành công", responses));
    }
}
