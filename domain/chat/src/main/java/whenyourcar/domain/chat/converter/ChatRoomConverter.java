package whenyourcar.domain.chat.converter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import whenyourcar.domain.chat.dto.ChatCommonResponse;
import whenyourcar.storage.mysql.data.entity.Room;
import whenyourcar.storage.mysql.data.entity.User;
import whenyourcar.storage.mysql.data.query.SearchRoomsQuery;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChatRoomConverter {
    public Room toChatRoom(User user1, User user2) {
        return Room.builder()
                .user1(user1)
                .user2(user2)
                .build();
    }

    public Page<ChatCommonResponse.ChatRoomResponse> toChatCommonResponse(Page<SearchRoomsQuery> searchRoomsQueries) {
        List<ChatCommonResponse.ChatRoomResponse> chatRoomResponses = searchRoomsQueries.getContent().stream()
                .map(room -> ChatCommonResponse.ChatRoomResponse.builder()
                        .roomId(room.getRoomId())
                        .user2Id(room.getUser2Id())
                        .picture(room.getPicture())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(chatRoomResponses, searchRoomsQueries.getPageable(), searchRoomsQueries.getTotalElements());
    }
}
