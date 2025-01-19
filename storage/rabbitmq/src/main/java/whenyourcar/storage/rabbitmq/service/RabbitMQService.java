package whenyourcar.storage.rabbitmq.service;

import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;

public interface RabbitMQService {
    public void createQueueForUser(Long userId);
    public void sendMessageToQueue(Long userId, ChatMessageDto chatMessageDto);
}
