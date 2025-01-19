package whenyourcar.domain.websocket.converter;

import org.springframework.stereotype.Component;
import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;
import whenyourcar.storage.redis.event.MessageEvent;

@Component
public class ChatMessageConverter {
    public ChatMessageDto toChatMEssageDto(MessageEvent messageEvent) {
        return ChatMessageDto.builder()
                .message(messageEvent.getMessage())
                .roomId(messageEvent.getRoomId())
                .sender(messageEvent.getSender())
                .timestamp(messageEvent.getTimestamps())
                .build();
    }
}
