package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.dto.request.CreateMedicineRequest;
import org.example.mobile.entity.Medicine;
import org.example.mobile.repository.MedicineRepository;
import org.example.mobile.service.MedicineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private final MedicineRepository medicineRepository;

    @Override
    @Transactional
    public Medicine createMedicine(CreateMedicineRequest request) {
        Medicine medicine = Medicine.builder()
                .name(request.getName())
                .imgUrl(request.getImgUrl())
                .description(request.getDescription())
                .use(request.getUse())
                .usage(request.getUsage())
                .precaution(request.getPrecaution())
                .build();
        
        return medicineRepository.save(medicine);
    }
} 