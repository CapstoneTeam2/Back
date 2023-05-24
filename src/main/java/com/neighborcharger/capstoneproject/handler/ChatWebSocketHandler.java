package com.neighborcharger.capstoneproject.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = extractRoomId(session);
        sessions.put(roomId, session);
        System.out.println(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = extractRoomId(session);
        String receivedMessage = message.getPayload();
        sendMessageToRoom(roomId, receivedMessage);
        System.out.println(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = extractRoomId(session);
        sessions.remove(roomId);
        System.out.println(session);

    }

    private void sendMessageToRoom(String roomId, String message) throws IOException {
        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
            String entryRoomId = entry.getKey();
            WebSocketSession entrySession = entry.getValue();

            if (roomId.equals(entryRoomId)) {
                entrySession.sendMessage(new TextMessage(message));
            }
            System.out.println(message);
        }
    }

    private String extractRoomId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String roomId = uri.substring(uri.lastIndexOf('/') + 1);
        System.out.println(roomId);
        return roomId;
    }
}