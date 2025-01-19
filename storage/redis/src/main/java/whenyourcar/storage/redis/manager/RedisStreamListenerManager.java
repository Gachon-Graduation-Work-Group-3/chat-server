package whenyourcar.storage.redis.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.stereotype.Component;
import whenyourcar.storage.redis.data.enums.RedisPrefixEnum;
import whenyourcar.storage.redis.serviceImpl.RedisStreamMessageListener;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class RedisStreamListenerManager {
    private final ConcurrentHashMap<Long, StreamMessageListenerContainer<String, MapRecord<String, String, String>>> containerMap = new ConcurrentHashMap<>();
    private final RedisConnectionFactory redisConnectionFactory;
    private final ApplicationEventPublisher applicationEventPublisher;
    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> createRedisStreamContainer(
            Long userId) {

        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(java.time.Duration.ofSeconds(2))
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(redisConnectionFactory, options);
        RedisStreamMessageListener redisStreamMessageListener = new RedisStreamMessageListener(applicationEventPublisher);
        redisStreamMessageListener.setUserId(userId);

        container.receive(
                Consumer.from( RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId), userId.toString()),
                StreamOffset.create(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(userId), ReadOffset.lastConsumed()),
                redisStreamMessageListener
        );

        container.start();

        containerMap.put(userId, container);
        return container;
    }

    public void removeContainer(Long userId) {
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = containerMap.remove(userId);
        if (container != null) {
            container.stop();
            System.out.printf("Redis Stream Container stopped and removed for Session: %s%n", userId);
        }
    }
}
