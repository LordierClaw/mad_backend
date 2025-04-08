package org.example.mobile.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateAdditionalUserInfoRequest {
    private String gender;
    private LocalDate dateOfBirth;
    private Integer weight;
    private Integer height;
}
