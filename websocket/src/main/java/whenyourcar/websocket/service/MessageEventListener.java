package whenyourcar.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import whenyourcar.redis.enums.RedisPrefixEnum;
import whenyourcar.redis.event.MessageEvent;
import whenyourcar.redis.service.RedisStreamService;
import whenyourcar.websocket.manager.WebSessionManager;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageEventListener {
    private final WebSessionManager webSessionManager;
    private final RedisStreamService redisStreamService;

    @EventListener
    public void handleMessageEvent(MessageEvent event) {
        System.out.println("group: " + event.getGroupName() );
        WebSocketSession session = webSessionManager.getWebSocketSessions(event.getGroupName());
        if (session != null && session.isOpen()) {
            try {
                redisStreamService.ackStream(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(event.getRoomId()), event.getGroupName(), event.getMessageId());
                session.sendMessage(new TextMessage(event.getMessage()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
