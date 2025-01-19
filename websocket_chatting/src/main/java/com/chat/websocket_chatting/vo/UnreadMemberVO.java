package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class UnreadMemberVO {
    private Long unreadMessageId;
    private Long userId;
    private Long studyId;

    private Boolean readCheck;
}
