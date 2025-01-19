package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class StudyMemberVO {
    private int studyNumber;
    private String userId;
    private String userName;
    private String authRole;
    private int messageCount;
}
