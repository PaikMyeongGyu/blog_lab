package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class StudyGroupVO {
    private int studyNumber;
    private String studyTitle;
    private String userId;
    private String studyDate;
    private String userName;

    // DB에는 없는 스터디장 설정 확인을 위한 변수
    private String authRole;
    private int messageCount;
}
