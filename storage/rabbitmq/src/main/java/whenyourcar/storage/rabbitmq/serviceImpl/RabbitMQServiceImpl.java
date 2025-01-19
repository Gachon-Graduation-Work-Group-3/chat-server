package whenyourcar.storage.rabbitmq.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;
import whenyourcar.storage.rabbitmq.service.RabbitMQService;

@Service
@RequiredArgsConstructor
public class RabbitMQServiceImpl implements RabbitMQService {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.chat-exchange.name}")
    private String CHAT_EXCHANGE_NAME;

    @Value("${rabbitmq.chat-queue.name}")
    private String CHAT_QUEUE_NAME;
    @Override
    public void createQueueForUser(Long userId) {
        String queueName = CHAT_QUEUE_NAME + userId;
        Queue userQueue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(userQueue);

        TopicExchange exchange = new TopicExchange(CHAT_EXCHANGE_NAME);
        Binding binding = BindingBuilder
                .bind(userQueue)
                .to(exchange)
                .with(queueName);
        rabbitAdmin.declareBinding(binding);
    }

    @Override
    public void sendMessageToQueue(Long userId, ChatMessageDto chatMessageDto) {
        String queueName = CHAT_QUEUE_NAME + userId;
        System.out.println("SEND TO QUEUE : " +queueName);
        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, queueName, chatMessageDto);
    }
}
