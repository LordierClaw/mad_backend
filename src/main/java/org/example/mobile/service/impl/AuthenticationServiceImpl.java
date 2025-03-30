package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.constant.Role;
import org.example.mobile.dto.request.ResetPasswordRequest;
import org.example.mobile.dto.request.SignInRequest;
import org.example.mobile.dto.request.SignUpRequest;
import org.example.mobile.dto.response.AuthResponse;
import org.example.mobile.entity.ResetPwdOtp;
import org.example.mobile.entity.User;
import org.example.mobile.exception.CommonException;
import org.example.mobile.repository.ResetPwdOtpRepository;
import org.example.mobile.repository.UserRepository;
import org.example.mobile.service.AuthenticationService;
import org.example.mobile.service.EmailService;
import org.example.mobile.service.JWTService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final ResetPwdOtpRepository resetPwdOtpRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final EmailService emailService;

    @Value("${password.reset.secret}")
    private String PASSWORD_RESET_SECRET;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signUp(SignUpRequest request) {
        if (userRepository.existsByLogin(request.getEmail(), request.getPhoneNumber())) {
            log.error("[AuthenticationService][signUp][Error] Username already exists");
            throw new CommonException(HttpStatus.BAD_REQUEST, "Tên đăng nhập đã tồn tại");
        }
        User user = new User();
        BeanUtils.copyProperties(request, user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login,
                            password
                    )
            );
        } catch (Exception e) {
            throw new CommonException(HttpStatus.FORBIDDEN, "Sai tên đăng nhập hoặc mật khẩu");
        }
        User user = userRepository.findByLogin(login).orElseThrow();
        return AuthResponse.builder()
                .accessToken(jwtService.generateToken(new HashMap<>(), user.getEmail()))
                .refreshToken(jwtService.generateRefreshToken(new HashMap<>(), user.getEmail()))
                .build();
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        int otp = request.getOtp();
        String password = request.getPassword();
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));

        ResetPwdOtp resetPwdOtp = resetPwdOtpRepository.findAndValidate(user.getId(), otp)
                .orElseThrow(() -> new CommonException(HttpStatus.BAD_REQUEST, "Mã xác thực không hợp lệ"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        resetPwdOtp.setIsUsed(true);
        resetPwdOtpRepository.save(resetPwdOtp);
    }

    @Override
    @Transactional
    public void sendResetPasswordMail(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng"));
        int otp = generateOtp();
        ResetPwdOtp resetPwdOtp = ResetPwdOtp.builder()
                .userId(user.getId())
                .otpCode(otp)
                .expiresAt(Timestamp.from(Instant.now().plusSeconds(15 * 60)))
                .build();
        resetPwdOtpRepository.save(resetPwdOtp);
        emailService.sendResetPasswordLink(user.getEmail(), otp);
    }

    private int generateOtp() {
        SecureRandom random = new SecureRandom();
        random.setSeed(PASSWORD_RESET_SECRET.getBytes(StandardCharsets.UTF_8));
        return 100_000 + random.nextInt(900_000);
    }
}
