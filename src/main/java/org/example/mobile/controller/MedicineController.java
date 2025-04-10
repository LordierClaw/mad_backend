package org.example.mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.CheckDrugInteractionRequest;
import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.dto.request.DeleteMedicinesRequest;
import org.example.mobile.dto.request.UpdateMedicineRequest;
import org.example.mobile.dto.response.DrugInteractionResponse;
import org.example.mobile.dto.response.MedicineResponse;
import org.example.mobile.entity.Medicine;
import org.example.mobile.service.MedicineService;
import org.example.mobile.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/medicines")
@RequiredArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;
    private final OpenAIService openAIService;
    
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
        Medicine medicine = medicineService.getMedicineByIdWithIngredients(id);
        MedicineResponse response = MedicineResponse.fromEntity(medicine);
        return ResponseEntity.ok(new Result("Lấy thông tin thuốc thành công", response));
    }
    
    @GetMapping
    public ResponseEntity<Result> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicinesWithIngredients();
        List<MedicineResponse> responses = MedicineResponse.fromEntities(medicines);
        return ResponseEntity.ok(new Result("Lấy danh sách thuốc thành công", responses));
    }
    
    @PostMapping("/check-interactions")
    public ResponseEntity<Result> checkDrugInteractions(@RequestBody @Valid CheckDrugInteractionRequest request) {
        Medicine newMedicine = medicineService.getMedicineById(request.getNewMedicineId());
        
        List<Medicine> existingMedicines = new ArrayList<>();
        if (request.getExistingMedicineIds() != null && !request.getExistingMedicineIds().isEmpty()) {
            for (Long id : request.getExistingMedicineIds()) {
                existingMedicines.add(medicineService.getMedicineById(id));
            }
        }
        
        String interactionDetails = openAIService.checkDrugInteractions(newMedicine, existingMedicines);
        
        DrugInteractionResponse response = DrugInteractionResponse.builder()
                .hasInteraction(interactionDetails != null)
                .interactionDetails(interactionDetails)
                .build();
        
        return ResponseEntity.ok(new Result("Kiểm tra tương tác thuốc thành công", response));
    }
}
