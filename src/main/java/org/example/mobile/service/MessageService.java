package org.example.mobile.service;

import org.example.mobile.entity.Doctor;
import org.example.mobile.entity.Message;
import org.example.mobile.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageService {
    public Message sendMessage(Long senderId, Long receiverId, String content);
//    public Message receiveMessage(Long senderId, Long receiverId);
    public List<Message> getConversation(Long user1Id, Long user2Id);

    public List<User> getAllUsersForDoctor(@Param("doctorId") Long doctorId);
    public List<Doctor> getAllDoctorsForUser(@Param("userId") Long userId);
}
