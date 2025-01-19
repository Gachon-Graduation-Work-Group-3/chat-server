package whenyourcar.storage.redis.converter;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.stereotype.Component;
import whenyourcar.storage.redis.data.dto.UnconsumedMessageDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UnconsumedMessageConverter {
    public UnconsumedMessageDto.UnconsumedMessages toUnconsumedMessage(List<MapRecord<String, Object, Object>> messages){
        Map<Long, List<UnconsumedMessageDto.UnconsumedMessageDetail>> groupedMessages = new HashMap<>();

        messages
                .forEach(message -> {
                    Map<Object, Object> payload = message.getValue();
                    Long roomId = ((Integer) payload.get("roomId")).longValue();
                    UnconsumedMessageDto.UnconsumedMessageDetail messageDetail = toUnconsumedMessageDetail(payload);

                    groupedMessages
                            .computeIfAbsent(roomId, k -> new ArrayList<>())
                            .add(messageDetail);
                });

        List<UnconsumedMessageDto.UnconsumedMessagePerRoom> unconsumedMessagePerRooms = toUnconsumedMessagePerRoom(groupedMessages);

        return UnconsumedMessageDto.UnconsumedMessages.builder()
                .unconsumedMessages(unconsumedMessagePerRooms)
                .build();
    }

    public List<UnconsumedMessageDto.UnconsumedMessagePerRoom> toUnconsumedMessagePerRoom(Map<Long, List<UnconsumedMessageDto.UnconsumedMessageDetail>> groupedMessages) {
        return groupedMessages.entrySet().stream()
                .map(entry -> UnconsumedMessageDto.UnconsumedMessagePerRoom.builder()
                        .roomId(entry.getKey())
                        .unconsumedMessageDetails(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public UnconsumedMessageDto.UnconsumedMessageDetail toUnconsumedMessageDetail(Map<Object, Object> payload) {
        Long sender = ((Integer) payload.get("sender")).longValue();
        String messageText = (String) payload.get("message");
        String timestamp = (String) payload.get("timestamp");

        return UnconsumedMessageDto.UnconsumedMessageDetail.builder()
                .message(messageText)
                .sender(sender)
                .timestamp(timestamp)
                .build();
    }
}
