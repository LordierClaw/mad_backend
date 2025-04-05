package org.example.mobile.controller;

import lombok.RequiredArgsConstructor;
import org.example.mobile.dto.request.MessageRequest;
import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.Message;
import org.example.mobile.entity.User;
import org.example.mobile.exception.CommonException;
import org.example.mobile.security.SecurityUtils;
import org.example.mobile.service.MessageService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest dto) {
        Message message = messageService.sendMessage(dto.getSenderId(), dto.getReceiverId(), dto.getContent());
        return ResponseEntity.ok(message);
    }

    @GetMapping("/conversation")
    public ResponseEntity<List<Message>> getConversation(@RequestParam Long user1,
                                                         @RequestParam Long user2) {
        return ResponseEntity.ok(messageService.getConversation(user1, user2));
    }

    @GetMapping("/getAllUsers/{id}")
    public ResponseEntity<List<User>> getAllUsersForDoctor(@PathVariable("id") Long id, @Param("doctorId") Long doctorId) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "Người dùng không có quyền");
        }
        return ResponseEntity.ok(messageService.getAllUsersForDoctor(doctorId));
    }

    @GetMapping("/getAllDoctors/{id}")
    public ResponseEntity<List<Doctor>> getAllDoctorsForUser(@PathVariable("id") Long id, @Param("userId") Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserLoginId();
        if (currentUserId == null || !currentUserId.equals(id)) {
            throw new CommonException(HttpStatus.BAD_REQUEST, "Người dùng không có quyền");
        }
        return ResponseEntity.ok(messageService.getAllDoctorsForUser(userId));
    }
}
