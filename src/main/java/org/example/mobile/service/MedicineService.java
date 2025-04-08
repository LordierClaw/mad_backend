package org.example.mobile.service;

import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.dto.request.UpdateMedicineRequest;
import org.example.mobile.entity.Medicine;

import java.util.List;

public interface MedicineService {
    Medicine createMedicine(CreateMedicineRequest request);
    
    Medicine updateMedicine(Long id, UpdateMedicineRequest request);
    
    void deleteMedicine(Long id);
    
    void deleteMedicines(List<Long> ids);
    
    Medicine getMedicineById(Long id);
    
    List<Medicine> getAllMedicines();
} 