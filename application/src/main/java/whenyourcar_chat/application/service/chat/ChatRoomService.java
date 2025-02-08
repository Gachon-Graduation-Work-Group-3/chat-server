package whenyourcar_chat.application.service.chat;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import whenyourcar_chat.application.dto.chat.common.ChatCommonResponse;
import whenyourcar_chat.domain.entity.User;

public interface ChatRoomService {
    public Page<ChatCommonResponse.ChatRoomResponseDto> getChatRooms(Pageable pageable, User user);
    public boolean isUserInRoom(Long roomId, Long userId);

    public void createChatRoom(User user1, User user2);
}
