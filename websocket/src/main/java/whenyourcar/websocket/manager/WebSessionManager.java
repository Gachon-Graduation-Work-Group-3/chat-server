package whenyourcar.websocket.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import whenyourcar.redis.enums.RedisPrefixEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class WebSessionManager {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void addWebSocketSession(WebSocketSession session,
                                    Long userId) {
        sessions.put(RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId), session);
    }

    public void deleteWebSocketSession(Long userId) {
        sessions.remove(RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId));
    }

    public WebSocketSession getWebSocketSessions(String groupName) {
        return sessions.get(groupName);
    }

    public Object getSessionAttribute(WebSocketSession session,
                                    String attribute) {
        return session.getAttributes().get(attribute);
    }

}
