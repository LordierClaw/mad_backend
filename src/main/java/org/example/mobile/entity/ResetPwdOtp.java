package org.example.mobile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "reset_pwd_otps")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResetPwdOtp {
    @Id
    private Long userId;

    @Column(name = "otp_code", nullable = false)
    private Integer otpCode;

    @Column(nullable = false)
    private Boolean isUsed = false;

    @Column(nullable = false)
    private Timestamp expiresAt;

    @Column(nullable = false)
    private Timestamp createdAt;
}
