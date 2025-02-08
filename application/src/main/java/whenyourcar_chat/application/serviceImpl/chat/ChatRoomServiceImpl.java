package whenyourcar_chat.application.serviceImpl.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar_chat.application.converter.chat.ChatRoomConverter;
import whenyourcar_chat.application.dto.chat.common.ChatCommonResponse;
import whenyourcar_chat.application.service.chat.ChatRoomService;
import whenyourcar_chat.common.code.exception.GeneralException;
import whenyourcar_chat.common.code.status.ErrorStatus;
import whenyourcar_chat.domain.entity.User;
import whenyourcar_chat.domain.repository.chat.ChatRoomRepository;
import whenyourcar_chat.domain.repository.user.UserCommonRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomConverter chatRoomConverter;

    @Override
    public Page<ChatCommonResponse.ChatRoomResponseDto> getChatRooms(Pageable pageable, User user) {
        return chatRoomConverter.toChatCommonResponse(chatRoomRepository.findPageRoomsByUserId(pageable, user.getId()));

    }

    @Override
    public boolean isUserInRoom(Long roomId, Long userId) {
        return chatRoomRepository.existsRoomByUserId(roomId, userId);
    }

    @Override
    public void createChatRoom(User user1, User user2) {
        if(!chatRoomRepository.existsRoomByUsers(user1.getId(), user2.getId())) {
            chatRoomRepository.save(chatRoomConverter.toChatRoom(user1, user2));
        }
    }
}
