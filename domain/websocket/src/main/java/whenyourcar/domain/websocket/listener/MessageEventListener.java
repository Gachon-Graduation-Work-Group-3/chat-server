package whenyourcar.domain.websocket.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import whenyourcar.domain.websocket.converter.ChatMessageConverter;
import whenyourcar.storage.rabbitmq.service.RabbitMQService;
import whenyourcar.storage.redis.event.MessageEvent;

@Service
@RequiredArgsConstructor
public class MessageEventListener {
    private final RabbitMQService rabbitMQService;
    private final ChatMessageConverter chatMessageConverter;
    @EventListener
    public void handleMessageEvent(MessageEvent event) {
        Long userId = event.getUserId();
        rabbitMQService.sendMessageToQueue(userId, chatMessageConverter.toChatMEssageDto(event));
    }
}
