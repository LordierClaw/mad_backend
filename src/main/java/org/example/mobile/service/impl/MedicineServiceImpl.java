package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.dto.request.UpdateMedicineRequest;
import org.example.mobile.entity.Medicine;
import org.example.mobile.exception.CommonException;
import org.example.mobile.repository.MedicineRepository;
import org.example.mobile.service.MedicineService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository medicineRepository;

    @Override
    @Transactional
    public Medicine createMedicine(CreateMedicineRequest request) {
        Medicine medicine = new Medicine();
        BeanUtils.copyProperties(request, medicine);
        return medicineRepository.save(medicine);
    }

    @Override
    @Transactional
    public Medicine updateMedicine(Long id, UpdateMedicineRequest request) {
        Medicine medicine = getMedicineById(id);
        
        if (StringUtils.hasText(request.getName())) {
            medicine.setName(request.getName());
        }
        
        if (request.getImgUrl() != null) {
            medicine.setImgUrl(request.getImgUrl());
        }
        
        if (request.getDescription() != null) {
            medicine.setDescription(request.getDescription());
        }
        
        if (request.getUse() != null) {
            medicine.setUse(request.getUse());
        }
        
        if (request.getUsage() != null) {
            medicine.setUsage(request.getUsage());
        }
        
        if (request.getPrecaution() != null) {
            medicine.setPrecaution(request.getPrecaution());
        }
        
        return medicineRepository.save(medicine);
    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
        Medicine medicine = getMedicineById(id);
        medicineRepository.delete(medicine);
    }

    @Override
    @Transactional
    public void deleteMedicines(List<Long> ids) {
        List<Medicine> medicines = medicineRepository.findAllById(ids);
        if (medicines.isEmpty()) {
            throw new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy thuốc nào để xóa");
        }
        medicineRepository.deleteAll(medicines);
    }

    @Override
    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy thuốc với ID: " + id));
    }
    
    @Override
    public Medicine getMedicineByIdWithIngredients(Long id) {
        return medicineRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy thuốc với ID: " + id));
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }
    
    @Override
    public List<Medicine> getAllMedicinesWithIngredients() {
        return medicineRepository.findAllWithIngredients();
    }
} 