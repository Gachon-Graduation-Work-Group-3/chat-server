package whenyourcar.storage.redis.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;
import whenyourcar.storage.redis.event.MessageEvent;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RedisStreamMessageListener implements StreamListener<String, MapRecord<String, String, String>> {
    private static final Logger logger = LoggerFactory.getLogger(RedisStreamMessageListener.class);
    private final ApplicationEventPublisher eventPublisher;
    private Long userId;

    @Override
    public void onMessage(MapRecord<String, String, String> message) {
        Map<String, Object> payload = message.getValue().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> parseValue(e.getValue()) // 유틸 메서드로 값 파싱
                ));

        String streamKey = message.getStream();
        Long roomId = (Long) payload.get("roomId");
        Long sender = (Long) payload.get("sender");
        String messageText = (String) payload.get("message");
        String timestamp = (String) payload.get("timestamp");

        logger.info("Received message from Redis Stream: {}, payload: {}, userId : {}, timestamp : {}", streamKey, payload, userId, timestamp);

        eventPublisher.publishEvent(new MessageEvent(
                this,
                messageText,
                roomId,
                userId,
                sender,
                timestamp));
    }
    private Object parseValue(String value) {
        try {
            if (value.matches("-?\\d+")) {
                return Long.parseLong(value); // 숫자 값으로 변환
            } else {
                return value; // 기본적으로 문자열로 처리
            }
        } catch (NumberFormatException e) {
            return value; // 숫자로 변환 실패 시 문자열로 반환
        }
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
