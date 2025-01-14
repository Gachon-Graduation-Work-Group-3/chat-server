package whenyourcar.redis.service;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface RedisStreamService {
    void createConsumerGroup(Long roomId, Long userId);
    void ackStream(String streamKey, String groupName, String messageId);
    void findAndTransportPendingMessages(WebSocketSession session,
                                         Long roomId,
                                         Long userId);
    void addMessageToStream(ObjectRecord<String, Map<String, Object>> record);
}
