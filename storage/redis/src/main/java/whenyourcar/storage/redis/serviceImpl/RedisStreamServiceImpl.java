package whenyourcar.storage.redis.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import whenyourcar.storage.redis.converter.UnconsumedMessageConverter;
import whenyourcar.storage.redis.data.enums.RedisPrefixEnum;
import whenyourcar.storage.redis.event.MessageEvent;
import whenyourcar.storage.redis.service.RedisStreamService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisStreamServiceImpl implements RedisStreamService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final UnconsumedMessageConverter unconsumedMessageConverter;
    @Override
    public void createConsumerGroup(Long userId){
        try {
            redisTemplate.opsForStream().createGroup(
                    RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(userId),
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
    public void findPendingMessages(Long userId) {
        List<MapRecord<String, Object, Object>> messages = redisTemplate.opsForStream().read(
                Consumer.from(RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId), userId.toString()),
                StreamReadOptions.empty(),
                StreamOffset.create(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(userId), ReadOffset.lastConsumed())
        );
        System.out.println(unconsumedMessageConverter.toUnconsumedMessage(messages));

        //eventPublisher.publishEvent(unconsumedMessageConverter.toUnconsumedMessage(messages));
    }

    @Override
    public void addMessageToStream(ObjectRecord<String, Map<String, Object>> record) {
        redisTemplate.opsForStream().add(record);
    }
}
