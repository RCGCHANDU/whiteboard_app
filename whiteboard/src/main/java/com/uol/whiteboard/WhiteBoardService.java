package com.uol.whiteboard;


import com.uol.whiteboard.DrawAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WhiteBoardService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    private static final String CHANNEL = "whiteboard_channel";  // Redis channel for broadcasting
    
    // Publish drawing action to Redis channel
    public void publishDrawAction(DrawAction drawAction) {
        redisTemplate.convertAndSend(CHANNEL, drawAction);  // Publish to Redis channel
    }

    // Method to handle incoming drawing actions from other nodes
    public void handleDrawActionFromRedis(DrawAction drawAction, WebSocketSession session) {
        try {
            String message = new ObjectMapper().writeValueAsString(drawAction);
            session.sendMessage(new TextMessage(message));  // Send drawing action to client
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
