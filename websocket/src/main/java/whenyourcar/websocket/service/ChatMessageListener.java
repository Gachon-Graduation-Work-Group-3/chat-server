package whenyourcar.websocket.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import whenyourcar.websocket.manager.WebSessionManager;

import java.time.Duration;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ChatMessageListener.class);
    private final RedisTemplate<String, Object> redisTemplate;
    private final WebSessionManager webSessionManager;

    public void listenToStream(String streamKey, Long roomId) {
        logger.info("Starting listener for Redis Stream: {}", streamKey);

        while (true) {
            try {
                var messages = redisTemplate.opsForStream().read(Object.class,
                        StreamReadOptions.empty().block(Duration.ofDays(1)),
                        StreamOffset.latest(streamKey));

                if (messages != null) {
                    for (var message : messages) {
                        String payload = (String) message.getValue();
                        logger.info("Received message from Redis Stream: {}, payload: {}", streamKey, payload);

                        // Redis Stream에서 수신한 메시지를 WebSocket으로 전송
                        Map<Long, WebSocketSession> roomSessions = webSessionManager.getWebSocketSession(roomId);
                        if (roomSessions != null) {
                            for (Map.Entry<Long, WebSocketSession> entry : roomSessions.entrySet()) {
                                WebSocketSession session = entry.getValue();
                                if (session != null && session.isOpen()) {
                                    session.sendMessage(new TextMessage(payload));
                                    logger.info("Message sent to userId: {} in roomId: {}", entry.getKey(), roomId);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error while reading Redis Stream: {}", streamKey, e);
            }
        }
    }
}
