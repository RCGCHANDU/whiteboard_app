package com.uol.whiteboard;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.CloseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.ArrayList;
import java.util.List;


import com.uol.whiteboard.DrawAction;

public class WhiteboardWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final List<DrawAction> drawActions = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        // Send the current state of the whiteboard to the new user
        String stateJson = objectMapper.writeValueAsString(drawActions);
        session.sendMessage(new TextMessage(stateJson));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Parse the incoming drawing data
        DrawAction drawAction = objectMapper.readValue(message.getPayload(), DrawAction.class);

        // Store the action in the history
        drawActions.add(drawAction);

        // Broadcast the drawing action to all connected clients
        String jsonMessage = objectMapper.writeValueAsString(drawAction);
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }
}