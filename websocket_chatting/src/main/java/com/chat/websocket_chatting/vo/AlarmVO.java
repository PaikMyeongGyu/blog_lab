package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class AlarmVO {
    private int alarmNo;
    private String userId;
    private String alarmType;
    private String createdAt;
    private int studyNumber;
}
