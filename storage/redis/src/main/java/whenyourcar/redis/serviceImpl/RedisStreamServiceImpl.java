package whenyourcar.redis.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;
import whenyourcar.redis.enums.RedisPrefixEnum;
import whenyourcar.redis.service.RedisStreamService;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisStreamServiceImpl implements RedisStreamService {

    private final RedisTemplate<String, Object> redisTemplate;
    @Override
    public void createConsumerGroup(Long roomId, Long userId){
        try {
            redisTemplate.opsForStream().createGroup(
                    RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(roomId),
                    RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId));
        } catch (Exception e) {
            System.out.println("Group already exists: " + e.getMessage());
        }
    }

    @Override
    public void ackStream(String streamKey, String groupName, String messageId) {
        redisTemplate.opsForStream().acknowledge(streamKey, groupName,  messageId);
    }

    @Override
    public void findAndTransportPendingMessages(WebSocketSession session,
                                                Long roomId,
                                                Long userId) {
        PendingMessages pendingMessages = redisTemplate.opsForStream().pending(
                RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(roomId),
                Consumer.from(RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId), userId.toString()),  Range.unbounded(), 100L);

        pendingMessages.toList()
                .forEach(pendingMessage -> {
                    Consumer consumerName = pendingMessage.getConsumer();
                    long pendingCount = pendingMessage.getTotalDeliveryCount();
                    System.out.println("Consumer: " + consumerName + " has " + pendingCount + " pending messages.");

                    /*List<ObjectRecord<String, Map<String, String>>> messages = redisTemplate.opsForStream()
                            .read(Consumer.from(groupName, userId.toString()),
                                    StreamReadOptions.empty().count(1),
                                    StreamOffset.from(pendingMessage.getIdAsString()));

                    try {
                        session.sendMessage(new TextMessage(message.get("message")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }*/
                });
    }

    @Override
    public void addMessageToStream(ObjectRecord<String, Map<String, Object>> record) {
        redisTemplate.opsForStream().add(record);
    }


}
