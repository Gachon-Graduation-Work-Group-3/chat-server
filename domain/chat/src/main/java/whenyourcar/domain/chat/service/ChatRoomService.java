package whenyourcar.domain.chat.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import whenyourcar.domain.chat.dto.common.ChatCommonResponse;
import whenyourcar.storage.mysql.data.entity.User;

public interface ChatRoomService {
    public Page<ChatCommonResponse.ChatRoomResponse> getChatRooms(Pageable pageable, Long userId);
    public boolean isUserInRoom(Long roomId, Long userId);

    public void createChatRoom(Long user1Id, Long user2Id);
}
