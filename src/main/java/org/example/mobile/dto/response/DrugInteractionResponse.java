package org.example.mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrugInteractionResponse {
    
    private boolean hasInteraction;
    
    private String interactionDetails;
} 