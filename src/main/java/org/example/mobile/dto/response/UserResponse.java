package org.example.mobile.dto.response;

import lombok.Data;
import org.example.mobile.constant.Role;

import java.time.LocalDate;

@Data
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String gender;

    private LocalDate dateOfBirth;

    private Integer weight;

    private Integer height;

    private Role role;
}
