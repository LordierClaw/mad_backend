package org.example.mobile.controller;

import lombok.RequiredArgsConstructor;
import org.example.mobile.common.Result;
import org.example.mobile.dto.request.MessageRequest;
import org.example.mobile.entity.Message;
import org.example.mobile.exception.CommonException;
import org.example.mobile.security.SecurityUtils;
import org.example.mobile.service.MessageService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Result> sendMessage(@RequestBody MessageRequest dto) {
        Message message = messageService.sendMessage(dto.getSenderId(), dto.getReceiverId(), dto.getContent());
        return ResponseEntity.ok(new Result("", message));
    }

    @GetMapping("/conversation")
    public ResponseEntity<Result> getConversation(@RequestParam Long user1,
                                                         @RequestParam Long user2) {
        return ResponseEntity.ok(new Result("", messageService.getConversation(user1, user2)));
    }

    @GetMapping("/getAllUsers/{id}")
    public ResponseEntity<Result> getAllUsersForDoctor(@PathVariable("id") Long id, @Param("doctorId") Long doctorId) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.UNAUTHORIZED, "Người dùng không có quyền");
        }
        return ResponseEntity.ok(new Result("", messageService.getAllUsersForDoctor(doctorId)));
    }

    @GetMapping("/getAllDoctors/{id}")
    public ResponseEntity<Result> getAllDoctorsForUser(@PathVariable("id") Long id, @Param("userId") Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.UNAUTHORIZED, "Người dùng không có quyền");
        }
        return ResponseEntity.ok(new Result("", messageService.getAllDoctorsForUser(userId)));
    }
}
