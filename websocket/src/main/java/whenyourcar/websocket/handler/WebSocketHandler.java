package whenyourcar.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import whenyourcar.redis.manager.RedisStreamListenerManager;
import whenyourcar.redis.service.RedisStreamService;
import whenyourcar.redis.serviceImpl.RedisStreamMessageListener;
import whenyourcar.redis.serviceImpl.RedisStreamMessagePublisher;
import whenyourcar.redis.serviceImpl.RedisStreamServiceImpl;
import whenyourcar.websocket.manager.WebSessionManager;


@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final WebSessionManager webSessionManager;

    private final RedisStreamListenerManager redisStreamListenerManager;
    private final RedisStreamService redisStreamService;
    private final RedisStreamMessagePublisher redisStreamMessagePublisher;
    private final RedisConnectionFactory connectionFactory;
    private final ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)  {
        Long roomId = (Long)webSessionManager.getSessionAttribute(session, "roomId");
        Long userId = (Long)webSessionManager.getSessionAttribute(session, "userId");
        String userMessage = message.getPayload();

        redisStreamMessagePublisher.publishMessage(userMessage, roomId, userId, session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long roomId = (Long)webSessionManager.getSessionAttribute(session, "roomId");
        Long userId = (Long)webSessionManager.getSessionAttribute(session, "userId");

        redisStreamService.createConsumerGroup(roomId, userId);
        redisStreamService.findAndTransportPendingMessages(session, roomId, userId);

        redisStreamListenerManager.createRedisStreamContainer(
                connectionFactory,
                applicationEventPublisher,
                roomId,
                userId,
                session.getId()
                );
        webSessionManager.addWebSocketSession(session, userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = (Long)webSessionManager.getSessionAttribute(session, "userId");
        redisStreamListenerManager.removeContainer(session.getId());
        webSessionManager.deleteWebSocketSession(userId);
    }
}
