package org.example.mobile.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMedicineRequest {
    @Size(max = 500, message = "Tên thuốc không được vượt quá 500 ký tự")
    private String name;
    
    @Size(max = 500, message = "URL hình ảnh không được vượt quá 500 ký tự")
    private String imgUrl;
    
    private String description;
    
    private String use;
    
    private String usage;
    
    private String precaution;
} 