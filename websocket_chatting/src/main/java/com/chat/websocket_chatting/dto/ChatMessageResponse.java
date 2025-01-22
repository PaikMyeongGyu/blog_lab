package com.chat.websocket_chatting.dto;

public record ChatMessageResponse(
        String username,
        String content
) {
}
