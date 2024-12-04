package com.uol.whiteboard;

import com.uol.whiteboard.WhiteboardService;

import com.uol.whiteboard.DrawAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.MessageListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class RedisSubscriber implements MessageListener {

    @Autowired
    private WhiteboardService whiteboardService;

    private List<WebSocketSession> sessions;

    @Override
    public void onMessage(org.springframework.data.redis.core.Message message, byte[] pattern) {
        try {
            DrawAction drawAction = new ObjectMapper().readValue(message.getBody(), DrawAction.class);
            // Update the sessions with the drawing action
            for (WebSocketSession session : sessions) {
                whiteboardService.handleDrawActionFromRedis(drawAction, session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSessions(List<WebSocketSession> sessions) {
        this.sessions = sessions;
    }
}
