package org.example.mobile.controller;


import lombok.RequiredArgsConstructor;
import org.example.mobile.common.Result;
import org.example.mobile.dto.response.TestResponse;
import org.example.mobile.entity.User;
import org.example.mobile.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test1")
    public String test1() {
        return "api này chỉ ai có role admin mới dùng đc";
    }

    @GetMapping("/test2")
    public String test2() {
        return "mặc định những api nằm trong đường dẫn: /api/v1/ đều cần role user hoặc admin.";
    }

    @GetMapping("/test3")
    public ResponseEntity<Result> test3() {
        // Lấy thông tin user hiện tại
        Optional<User> user = SecurityUtils.getCurrentUserLogin();
        if (user.isPresent()) {
//            return "User hiện tại là: " + user.get().getId() + " " + user.get().getUsername();
            TestResponse testResponse = TestResponse.builder()
                    .id(user.get().getId())
                    .username(user.get().getUsername())
                    .build();
            return ResponseEntity.ok(new Result("Lấy id user hiện tại thành công", testResponse));
        }
        return ResponseEntity.ok(new Result(""));
    }
}
