package com.chat.websocket_chatting.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessageVO {
    private Long chatMessageId;
    private Long studyId;
    private Long userId;
    private String messageContent;
    private LocalDateTime messageDate;
    private Long unreadCount;

    // DB에 없는 필요한 변수
    private String type;
}
