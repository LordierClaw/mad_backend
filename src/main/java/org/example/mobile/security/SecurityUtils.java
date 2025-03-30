package org.example.mobile.security;

import org.example.mobile.entity.User;
import org.example.mobile.exception.CommonException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityUtils {

    public static Long getCurrentUserLoginId() {
        Optional<User> user = getCurrentUserLogin();
        if (user.isPresent() && user.get().getId() != null) {
            return user.get().getId();
        }
        throw new CommonException(HttpStatus.UNAUTHORIZED, "Không xác thực được người dùng");
    }

    public static Optional<User> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static User extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }
}
