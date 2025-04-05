package org.example.mobile.controller;

import lombok.RequiredArgsConstructor;
import org.example.mobile.dto.request.MessageRequest;
import org.example.mobile.entity.Message;
import org.example.mobile.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
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
}
