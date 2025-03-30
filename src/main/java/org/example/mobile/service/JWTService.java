package org.example.mobile.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String extractUserName(String token);

    String generateToken(Map<String, Object> claims, String username);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> claims, String username);

    boolean isTokenExpired(String jwt);
}
