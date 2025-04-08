package org.example.mobile.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserInfoRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth;

    private Integer weight;

    private Integer height;
}
