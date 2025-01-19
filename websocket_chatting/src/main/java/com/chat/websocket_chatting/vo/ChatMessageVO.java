package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class ChatMessageVO {
    private int messageId;
    private int studyNumber;
    private String messageContent;
    private String messageRegisterDate;
    private String userId;
    private String userName;
    private int unreadCount;

    // DB에 없는 필요한 변수
    private String type;
}
