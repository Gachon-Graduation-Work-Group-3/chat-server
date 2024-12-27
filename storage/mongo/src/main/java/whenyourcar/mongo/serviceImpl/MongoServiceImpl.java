package whenyourcar.mongo.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whenyourcar.mongo.data.ChatMessage;
import whenyourcar.mongo.data.ChatRoom;
import whenyourcar.mongo.repository.ChatMessageRepository;
import whenyourcar.mongo.repository.ChatRoomRepository;
import whenyourcar.mongo.service.MongoService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MongoServiceImpl implements MongoService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public void saveChatMessage(Long senderId, Long roomId, String message) {
        chatMessageRepository.save(ChatMessage.builder()
                .message(message)
                .roomId(roomId)
                .senderId(senderId)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @Override
    public ChatRoom createOrFindRoom(Long user1Id, Long user2Id) {
        return chatRoomRepository.findByUser1IdAndUser2Id(user1Id, user2Id)
                .or(() -> chatRoomRepository.findByUser1IdAndUser2Id(user2Id, user1Id))
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .user1Id(user1Id)
                            .user2Id(user2Id)
                            .build();
                    return chatRoomRepository.save(newRoom);
                });
    }
}
