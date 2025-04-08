package org.example.mobile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.DoctorInfo;
import org.example.mobile.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DoctorResponse {
    private Long id;
    private String specialty;
    
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dateOfBirth;
    private Integer weight;
    private Integer height;
    private String role;
    
    private String title;
    private String description;
    private Integer orderNum;
} 