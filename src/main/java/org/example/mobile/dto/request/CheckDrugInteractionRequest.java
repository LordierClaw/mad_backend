package org.example.mobile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckDrugInteractionRequest {
    
    @NotNull(message = "Thuốc mới không được để trống")
    private Long newMedicineId;
    
    private List<Long> existingMedicineIds;
} 