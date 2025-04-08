package org.example.mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.mobile.entity.Medicine;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MedicineResponse {
    private Long id;
    private String name;
    private String imgUrl;
    private String description;
    private String use;
    private String usage;
    private String precaution;
    
    public static MedicineResponse fromEntity(Medicine medicine) {
        return MedicineResponse.builder()
                .id(medicine.getId())
                .name(medicine.getName())
                .imgUrl(medicine.getImgUrl())
                .description(medicine.getDescription())
                .use(medicine.getUse())
                .usage(medicine.getUsage())
                .precaution(medicine.getPrecaution())
                .build();
    }
    
    public static List<MedicineResponse> fromEntities(List<Medicine> medicines) {
        return medicines.stream()
                .map(MedicineResponse::fromEntity)
                .collect(Collectors.toList());
    }
} 