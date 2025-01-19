package com.chat.websocket_chatting.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StudyVO {
    private Long studyId;
    private String studyTitle;
    private Long studyLeaderId;
    private LocalDateTime studyDate;

    // DB에는 없는 스터디장 설정 확인을 위한 변수
    private String authRole;
    private Long messageCount;
}
