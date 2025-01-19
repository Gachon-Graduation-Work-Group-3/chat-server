package whenyourcar.storage.redis.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Service;
import whenyourcar.storage.redis.data.enums.RedisPrefixEnum;
import whenyourcar.storage.redis.service.RedisStreamService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisStreamMessagePublisher {
    private final RedisStreamService redisStreamService;

    public void publishMessage(Long roomId, Long sender, Long user1Id, Long user2Id, String message) {
        Map<String, Object> messageData = Map.of(
                "message",message,
                "roomId", roomId,
                "sender", sender,
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
        );
        ObjectRecord<String, Map<String, Object>> record1 = StreamRecords.newRecord()
                .ofObject(messageData)
                .withStreamKey(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(user1Id));

        redisStreamService.addMessageToStream(record1);

        ObjectRecord<String, Map<String, Object>> record2 = StreamRecords.newRecord()
                .ofObject(messageData)
                .withStreamKey(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(user2Id));

        redisStreamService.addMessageToStream(record2);
    }
}
