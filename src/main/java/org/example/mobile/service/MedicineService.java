package org.example.mobile.service;

import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.entity.Medicine;

public interface MedicineService {
    Medicine createMedicine(CreateMedicineRequest request);
} 