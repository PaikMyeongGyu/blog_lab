package com.chat.websocket_chatting.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmVO {
    private Long alarmId;
    private Long studyId;
    private Long userId;
    private String alarmType;
    private LocalDateTime createdAt;
    private String alarmTitle;
}
