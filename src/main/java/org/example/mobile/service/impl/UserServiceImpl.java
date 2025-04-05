package org.example.mobile.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.dto.request.UpdateAdditionalUserInfoRequest;
import org.example.mobile.dto.request.UpdateUserInfoRequest;
import org.example.mobile.entity.User;
import org.example.mobile.exception.CommonException;
import org.example.mobile.repository.UserRepository;
import org.example.mobile.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) {
        log.info("[UserService][createUser][Handle] user: {}", user.getUsername());
        userRepository.save(user);
        log.info("[UserService][createUser][Success] user: {}", user.getUsername());
    }

    @Override
    @Transactional
    public void updateUser(Long id, UpdateAdditionalUserInfoRequest request) {
        User user = getUserById(id);
        BeanUtils.copyProperties(request, user);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(Long id, UpdateUserInfoRequest request) {
        User user = getUserById(id);
        BeanUtils.copyProperties(request, user);
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new CommonException(HttpStatus.NOT_FOUND, "Người dùng có: " + id + " không tìm thấy")
        );
    }

    @Override
    public UserDetailsService userDetailsService() {
        return login -> userRepository.findByLogin(login)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng" + login));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, "Không tìm thấy người dùng" + email));
    }

    @Override
    public boolean existsByLogin(String email, String phoneNumber) {
        return userRepository.existsByLogin(email, phoneNumber);
    }
}
