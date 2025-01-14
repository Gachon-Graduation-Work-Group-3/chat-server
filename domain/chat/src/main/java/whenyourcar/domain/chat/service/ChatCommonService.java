package whenyourcar.domain.chat.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import whenyourcar.domain.chat.dto.ChatCommonResponse;
import whenyourcar.storage.mysql.data.query.SearchRoomsQuery;

public interface ChatCommonService {
    public void postChatRoom(Long user1Id, Long user2Id);
    public Page<ChatCommonResponse.ChatRoomResponse> getChatRooms(Pageable pageable, Long userId);
}
