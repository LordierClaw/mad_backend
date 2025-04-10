package org.example.mobile.dto.response;

import lombok.*;
import org.example.mobile.entity.MedicineIngredient;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IngredientResponse {
    private Long id;
    private String name;
    private String amount;
    
    public static IngredientResponse fromEntity(MedicineIngredient ingredient) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .amount(ingredient.getAmount())
                .build();
    }
    
    public static List<IngredientResponse> fromEntities(List<MedicineIngredient> ingredients) {
        return ingredients.stream()
                .map(IngredientResponse::fromEntity)
                .collect(Collectors.toList());
    }
} 