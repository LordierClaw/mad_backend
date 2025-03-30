package org.example.mobile.repository;

import org.example.mobile.entity.ResetPwdOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ResetPwdOtpRepository extends JpaRepository<ResetPwdOtp, Long> {
    @Query("""
        SELECT r FROM ResetPwdOtp r
        WHERE r.userId = :userId
          AND r.otpCode = :otp
          AND r.isUsed = false
          AND r.expiresAt < current_timestamp
    """)
    Optional<ResetPwdOtp> findAndValidate(Long userId, Integer otp);
}
