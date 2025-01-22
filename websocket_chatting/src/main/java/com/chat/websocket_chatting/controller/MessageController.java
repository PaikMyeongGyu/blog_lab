package com.chat.websocket_chatting.controller;

import com.chat.websocket_chatting.dto.ChatMessageRequest;
import com.chat.websocket_chatting.dto.ChatMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/chat.{chatRoomId}")
    @SendTo("/subscribe/chat.{chatRoomId}")
    public ChatMessageResponse sendMessage(ChatMessageRequest request, @DestinationVariable Long chatRoomId) {
        return new ChatMessageResponse(request.username(), request.content());
    }

    @MessageExceptionHandler
    public void handleException(RuntimeException e) {
        log.info("Exception: {}", e.getMessage());
    }
}
