package whenyourcar.domain.chat.serviceImpl;

import code.exception.AuthenticationException;
import code.exception.GeneralException;
import code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar.domain.chat.converter.ChatMessageConverter;
import whenyourcar.domain.chat.dto.message.ChatMessageResponse;
import whenyourcar.domain.chat.service.ChatMessageService;
import whenyourcar.storage.mysql.data.entity.Chat;
import whenyourcar.storage.mysql.data.entity.Room;
import whenyourcar.storage.mysql.data.entity.User;
import whenyourcar.storage.mysql.data.query.SearchChatsQuery;
import whenyourcar.storage.mysql.data.query.SearchRoomUsersQuery;
import whenyourcar.storage.mysql.repository.chat.ChatRepository;
import whenyourcar.storage.mysql.repository.chat.ChatRoomRepository;
import whenyourcar.storage.mysql.repository.user.UserCommonRepository;
import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final RabbitAdmin rabbitAdmin;
    private final RabbitTemplate rabbitTemplate;

    private final UserCommonRepository userCommonRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    private final ChatMessageConverter chatMessageConverter;

    @Value("${rabbitmq.chat-exchange.name}")
    private String CHAT_EXCHANGE_NAME;
    @Value("${rabbitmq.chat-queue.name}")
    private String CHAT_QUEUE_NAME;

    @Override
    public ChatMessageResponse.ChatMessageResponseSet getChat(Pageable pageable, Long roomId, Long userId) {
        Page<SearchChatsQuery> chats = chatRepository.findChatByRoomId(pageable, roomId);
        User sender = userCommonRepository.findUserById(userId)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_IS_NOT_EXIST));
        return chatMessageConverter.toGetMessageDto(chats, roomId, sender);
    }

    @Override
    public String saveChat(Long roomId, Long sender, String message) {
        User user = userCommonRepository.findUserById(sender)
                .orElseThrow(()-> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));

        Room room = chatRoomRepository.findRoomById(roomId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ROOM_IS_NOT_EXIST));

        Chat chat = chatRepository.save(chatMessageConverter.toChatDto(room, user, message));
        return chat.getId();
    }

    @Override
    public void createMessageQueueForUser(Long userId) {
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
    public void sendMessageToMessageQueue(Long roomId, String chatId, Long senderId, String messagePayload) {
        SearchRoomUsersQuery searchRoomUsersQuery = chatRoomRepository.findUsersByRoomId(roomId);
        ChatMessageDto chatMessageDto = chatMessageConverter.toChatMEssageDto(chatId, senderId, roomId, messagePayload);

        String queueName1 = CHAT_QUEUE_NAME + searchRoomUsersQuery.getUser1Id();
        String queueName2 = CHAT_QUEUE_NAME + searchRoomUsersQuery.getUser2Id();
        System.out.println("SEND TO QUEUE : " +queueName1);
        System.out.println("SEND TO QUEUE : " +queueName2);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setTimestamp(new Date()); // 현재 시간을 타임스탬프로 설정

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, queueName1, chatMessageDto, message -> {
            message.getMessageProperties().setTimestamp(new Date());
            return message;
        });

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, queueName2, chatMessageDto, message -> {
            message.getMessageProperties().setTimestamp(new Date());
            return message;
        });
    }
}
