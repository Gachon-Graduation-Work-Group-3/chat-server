package whenyourcar.redis.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.stereotype.Service;
import whenyourcar.redis.enums.RedisPrefixEnum;
import whenyourcar.redis.service.RedisStreamService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisStreamMessagePublisher {
    private final RedisStreamService redisStreamService;

    public void publishMessage(String message,
                               Long roomId,
                               Long userId,
                               String sessionId) {
        Map<String, Object> messageData = Map.of(
                "message",message,
                "groupName", RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId),
                "roomId", roomId,
                "sessionId", sessionId
        );
        ObjectRecord<String, Map<String, Object>> record = StreamRecords.newRecord()
                .ofObject(messageData)
                .withStreamKey(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(roomId));

        redisStreamService.addMessageToStream(record);

    }
}
