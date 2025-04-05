package org.example.mobile.service;

import org.example.mobile.dto.request.UpdateAdditionalUserInfoRequest;
import org.example.mobile.dto.request.UpdateUserInfoRequest;
import org.example.mobile.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    List<User> getAllUser();

    void createUser(User user);

    void updateUser(Long id, UpdateAdditionalUserInfoRequest request);

    void updateUser(Long id, UpdateUserInfoRequest request);

    User getUserById(Long id);

    UserDetailsService userDetailsService();

    boolean existsByEmail(String email);

    User getUserByEmail(String email);

    boolean existsByLogin(String email ,String phoneNumber);
}
