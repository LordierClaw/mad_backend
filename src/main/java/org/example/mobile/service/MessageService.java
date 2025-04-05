package org.example.mobile.service;

import org.example.mobile.entity.Message;

import java.util.List;

public interface MessageService {
    public Message sendMessage(Long senderId, Long receiverId, String content);

    public List<Message> getConversation(Long user1Id, Long user2Id);
}
