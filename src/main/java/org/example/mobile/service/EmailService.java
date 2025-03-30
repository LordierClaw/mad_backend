package org.example.mobile.service;

public interface EmailService {
    void sendResetPasswordLink(String email, int otpCode);
}
