package org.example.mobile.service;

import org.example.mobile.entity.Medicine;

import java.util.List;

public interface OpenAIService {
    String checkDrugInteractions(Medicine newMedicine, List<Medicine> existingMedicines);
} 