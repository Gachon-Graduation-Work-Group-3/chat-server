package whenyourcar.websocket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Service;
import whenyourcar.mongo.service.MongoService;

@Service
@RequiredArgsConstructor
public class ChatMessagePublisher {
    private final RedisTemplate<String, Object> redisTemplate;
    private final MongoService mongoService;

    public void publishMessage(String message,
                               String streamKey,
                               Long roomId,
                               Long userId) {
        ObjectRecord<String, String> record = StreamRecords.newRecord()
                .ofObject(message)
                .withStreamKey(streamKey);

        redisTemplate.opsForStream().add(record);
        mongoService.saveChatMessage(userId, roomId, message);
    }
}
