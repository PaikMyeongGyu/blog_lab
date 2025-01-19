package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class UnreadMemberVO {
    private int messageId;
    private String userId;
    private int readCheck;
    private int studyNumber;
}
