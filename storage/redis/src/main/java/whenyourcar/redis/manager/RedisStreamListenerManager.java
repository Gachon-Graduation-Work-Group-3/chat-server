package whenyourcar.redis.manager;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer.StreamMessageListenerContainerOptions;
import org.springframework.stereotype.Component;
import whenyourcar.redis.enums.RedisPrefixEnum;
import whenyourcar.redis.serviceImpl.RedisStreamMessageListener;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisStreamListenerManager {
    private final ConcurrentHashMap<String, StreamMessageListenerContainer<String, MapRecord<String, String, String>>> containerMap = new ConcurrentHashMap<>();

    public StreamMessageListenerContainer<String, MapRecord<String, String, String>> createRedisStreamContainer(
            RedisConnectionFactory connectionFactory,
            ApplicationEventPublisher applicationEventPublisher,
            Long roomId,
            Long userId,
            String sessionId) {

        StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options =
                StreamMessageListenerContainerOptions.builder()
                        .pollTimeout(java.time.Duration.ofSeconds(2))
                        .build();

        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container =
                StreamMessageListenerContainer.create(connectionFactory, options);
        RedisStreamMessageListener redisStreamMessageListener = new RedisStreamMessageListener(applicationEventPublisher);
        redisStreamMessageListener.setUserId(userId);

        container.receive(
                Consumer.from( RedisPrefixEnum.GROUP_NAME_PREFIX.getValue(userId), userId.toString()),
                StreamOffset.create(RedisPrefixEnum.STREAM_KEY_PREFIX.getValue(roomId), ReadOffset.lastConsumed()),
                redisStreamMessageListener
        );

        container.start();

        containerMap.put(sessionId, container);
        return container;
    }

    public void removeContainer(String sessionId) {
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> container = containerMap.remove(sessionId);
        if (container != null) {
            container.stop();
            System.out.printf("Redis Stream Container stopped and removed for Session: %s%n", sessionId);
        }
    }
}
