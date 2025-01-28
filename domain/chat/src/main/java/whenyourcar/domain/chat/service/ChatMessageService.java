package whenyourcar.domain.chat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import whenyourcar.domain.chat.dto.message.ChatMessageResponse;
import whenyourcar.storage.mysql.data.entity.Chat;
import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;

public interface ChatMessageService {

    public ChatMessageResponse.ChatMessageResponseSet getChat(Pageable pageable, Long roomId, Long userId);
    public String saveChat(Long roomId, Long sender, String message);
    public void createMessageQueueForUser(Long userId);
    public void sendMessageToMessageQueue(Long roomId, String chatId, Long senderId, String message);
}
