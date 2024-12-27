package whenyourcar.websocket.manager;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSessionManager {
    private static final Map<Long, Map<Long, WebSocketSession>> sessions = new ConcurrentHashMap<>();

    public void addWebSocketSession(WebSocketSession session,
                                    Long roomId,
                                    Long userId) {
        sessions.computeIfAbsent(roomId, key -> new ConcurrentHashMap<>())
                .put(userId, session);
    }

    public void deleteWebSocketSession(Long roomId,
                                       Long userId) {
        Map<Long, WebSocketSession> sessionMap = sessions.get(roomId);
        if (sessionMap != null) {
            sessionMap.remove(userId);
            if (sessionMap.isEmpty()) {
                sessions.remove(roomId);
            }
        }
    }

    public Map<Long, WebSocketSession> getWebSocketSession(Long roomId) {
        return sessions.get(roomId);
    }

    public Long getSessionAttribute(WebSocketSession session,
                                    String attribute) {
        return (Long) session.getAttributes().get(attribute);
    }
}
