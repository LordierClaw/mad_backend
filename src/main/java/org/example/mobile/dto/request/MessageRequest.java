package org.example.mobile.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MessageRequest {
    private Long senderId;
    private Long receiverId;
    private String content;
}
