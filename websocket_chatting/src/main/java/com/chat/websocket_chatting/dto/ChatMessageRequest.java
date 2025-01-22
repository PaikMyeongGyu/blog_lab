package com.chat.websocket_chatting.dto;

public record ChatMessageRequest(
        String username,
        String content
) {
}
