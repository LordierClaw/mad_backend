package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.entity.Message;
import org.example.mobile.repository.MessageRepository;
import org.example.mobile.service.MessageService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public Message sendMessage(Long senderId, Long receiverId, String content){
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getConversation(Long user1Id, Long user2Id){
        return messageRepository.getConversationBetweenUsers(user1Id, user2Id);
    }
}
