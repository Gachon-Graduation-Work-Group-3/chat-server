package whenyourcar.storage.redis.service;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

public interface RedisStreamService {
    void createConsumerGroup(Long userId);
    void ackStream(String streamKey, String groupName, String messageId);
    void findPendingMessages(Long userId);
    void addMessageToStream(ObjectRecord<String, Map<String, Object>> record);
}
