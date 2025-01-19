package com.chat.websocket_chatting.vo;

import lombok.Data;

@Data
public class UserVO {
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
}
