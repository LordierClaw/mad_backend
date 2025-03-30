package org.example.mobile.service;


import org.example.mobile.dto.request.ResetPasswordRequest;
import org.example.mobile.dto.request.SignInRequest;
import org.example.mobile.dto.request.SignUpRequest;
import org.example.mobile.dto.response.AuthResponse;

public interface AuthenticationService {
    void signUp(SignUpRequest user);

    AuthResponse signIn(SignInRequest request);

    void resetPassword(ResetPasswordRequest request);

    void sendResetPasswordMail(String email);
}
