
package whenyourcar.domain.chat.converter;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import whenyourcar.domain.chat.dto.message.ChatMessageResponse;
import whenyourcar.storage.mysql.data.entity.Chat;
import whenyourcar.storage.mysql.data.entity.Room;
import whenyourcar.storage.mysql.data.entity.User;
import whenyourcar.storage.mysql.data.query.SearchChatsQuery;
import whenyourcar.storage.rabbitmq.dto.ChatMessageDto;

import java.util.UUID;

@Component
public class ChatMessageConverter {
    public ChatMessageDto toChatMEssageDto(String id,
                                           Long userId,
                                           Long roomId,
                                           String message) {
        return ChatMessageDto.builder()
                .id(id)
                .message(message)
                .roomId(roomId)
                .sender(userId)
                .build();
    }

    public Chat toChatDto(Room room,
                       User user,
                       String message){
        return Chat.builder()
                .id(UUID.randomUUID().toString())
                .message(message)
                .room(room)
                .user(user)
                .build();
    }

    public ChatMessageResponse.ChatMessageResponseSet toGetMessageDto(
            Page<SearchChatsQuery> chats,
            Long roomId,
            User sender
    ){
        return ChatMessageResponse.ChatMessageResponseSet.builder()
                .chatMessages(chats)
                .senderPicture(sender.getPicture())
                .senderName(sender.getName())
                .roomId(roomId)
                .build();
    }
}
