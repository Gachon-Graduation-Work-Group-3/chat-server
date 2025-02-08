package whenyourcar_chat.application.service.chat;

import org.springframework.data.domain.Pageable;
import whenyourcar_chat.application.dto.chat.message.ChatMessageResponse;

public interface ChatMessageService {

    public ChatMessageResponse.ChatMessageResponseSetDto getChat(Pageable pageable, Long roomId, Long userId);
    public String saveChat(Long roomId, Long sender, String message);
    public void createMessageQueueForUser(Long userId);
    public void sendMessageToMessageQueue(Long roomId, String chatId, Long senderId, String message);
}
