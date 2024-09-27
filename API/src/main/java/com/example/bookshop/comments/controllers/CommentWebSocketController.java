package com.example.bookshop.comments.controllers;

import com.example.bookshop.comments.controllers.dto.CommentResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CommentWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public CommentWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Khi có bình luận mới, gửi tới tất cả các client đang kết nối qua WebSocket
    public void broadcastNewComment(CommentResponse commentDTO) {
        this.messagingTemplate.convertAndSend("/topic/comments", commentDTO);
    }
}
