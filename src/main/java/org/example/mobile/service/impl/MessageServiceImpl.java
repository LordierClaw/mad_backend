package org.example.mobile.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.Message;
import org.example.mobile.entity.User;
import org.example.mobile.repository.MessageRepository;
import org.example.mobile.service.MessageService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public Message sendMessage(Long senderId, Long receiverId, String content){
        Message message = new Message();
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        message.setContent(content);
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

//    public Message receiveMessage(Long senderId, Long receiverId){
//        return messageRepository.findById(receiverId).orElse(null);
//    }

    @Override
    public List<Message> getConversation(Long user1Id, Long user2Id){
        return messageRepository.getConversationBetweenUsers(user1Id, user2Id);
    }

    @Override
    public List<User> getAllUsersForDoctor(@Param("doctorId") Long doctorId) {
        return messageRepository.getAllUsersForDoctor(doctorId);
    }

    @Override
    public List<Doctor> getAllDoctorsForUser(@Param("userId") Long userId) {
        return messageRepository.getAllDoctorsForUser(userId);
    }
}
