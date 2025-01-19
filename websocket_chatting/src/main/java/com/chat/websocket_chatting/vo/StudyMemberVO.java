package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class StudyMemberVO {
    private Long studyMemberId;
    private Long studyId;
    private Long userId;
    private String userNickname;
    private String authRole;
    private Long messageCount;
}
