package org.example.mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.RefreshTokenRequest;
import org.example.mobile.dto.request.ResetPasswordRequest;
import org.example.mobile.dto.request.SignInRequest;
import org.example.mobile.dto.request.SignUpRequest;
import org.example.mobile.dto.response.AuthResponse;
import org.example.mobile.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/sign-up")
    public ResponseEntity<Result> signUp(@RequestBody @Valid SignUpRequest request) {
        authenticationService.signUp(request);
        return ResponseEntity.ok(new Result("Đăng ký thành công"));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<Result> signIn(@RequestBody @Valid SignInRequest request) {
        AuthResponse response = authenticationService.signIn(request);
        return ResponseEntity.ok(new Result("Đăng nhập thành công", response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Result> forgotPassword(@RequestParam String login) {
        authenticationService.sendResetPasswordMail(login);
        return ResponseEntity.ok(new Result("Đã gửi yêu cầu thay đổi mật khẩu"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Result> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        authenticationService.resetPassword(request);
        return ResponseEntity.ok(new Result("Đã thay đổi mật khẩu"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Result> refreshToken(@RequestBody RefreshTokenRequest request) {
        AuthResponse response = authenticationService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(new Result("Làm mới token thành công", response));
    }

}

