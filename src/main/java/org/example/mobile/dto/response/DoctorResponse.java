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
    
    public static DoctorResponse fromEntity(Doctor doctor, DoctorInfo doctorInfo) {
        User user = doctor.getUser();
        
        return DoctorResponse.builder()
                .id(doctor.getId())
                .specialty(doctor.getSpecialty())
                .firstName(user != null ? user.getFirstName() : null)
                .lastName(user != null ? user.getLastName() : null)
                .email(user != null ? user.getEmail() : null)
                .phoneNumber(user != null ? user.getPhoneNumber() : null)
                .gender(user != null ? user.getGender() : null)
                .dateOfBirth(user != null ? user.getDateOfBirth() : null)
                .weight(user != null ? user.getWeight() : null)
                .height(user != null ? user.getHeight() : null)
                .role(user != null ? user.getRole().name() : null)
                .title(doctorInfo != null ? doctorInfo.getTitle() : null)
                .description(doctorInfo != null ? doctorInfo.getDescription() : null)
                .orderNum(doctorInfo != null ? doctorInfo.getOrder() : null)
                .build();
    }
    
    public static List<DoctorResponse> fromEntities(List<Doctor> doctors, List<DoctorInfo> doctorInfos) {
        return doctors.stream()
                .map(doctor -> {
                    DoctorInfo info = doctorInfos.stream()
                            .filter(di -> di.getDoctorId().equals(doctor.getId()))
                            .findFirst()
                            .orElse(null);
                    
                    return fromEntity(doctor, info);
                })
                .collect(Collectors.toList());
    }
} 