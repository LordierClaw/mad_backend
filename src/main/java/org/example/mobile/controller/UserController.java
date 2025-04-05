package org.example.mobile.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.UpdateAdditionalUserInfoRequest;
import org.example.mobile.dto.request.UpdateUserInfoRequest;
import org.example.mobile.dto.response.UserResponse;
import org.example.mobile.entity.User;
import org.example.mobile.exception.CommonException;
import org.example.mobile.security.SecurityUtils;
import org.example.mobile.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/{id}/update-additional")
    public ResponseEntity<Result> updateAdditionalInformation(
            @PathVariable("id") Long id,
            @RequestBody UpdateAdditionalUserInfoRequest request
    ) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.UNAUTHORIZED, "Người dùng không có quyền");
        }
        userService.updateUser(id, request);
        return ResponseEntity.ok(new Result("Đã cập nhật thành công!"));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Result> updateInformation(
            @PathVariable("id") Long id,
            @RequestBody UpdateUserInfoRequest request
    ) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.UNAUTHORIZED, "Người dùng không có quyền");
        }
        userService.updateUser(id, request);
        return ResponseEntity.ok(new Result("Đã cập nhật thành công!"));
    }

    @GetMapping("/detail")
    public ResponseEntity<Result> getDetail() {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        User user = userService.getUserById(currentUserId);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        return ResponseEntity.ok(new Result("", userResponse));
    }
}
