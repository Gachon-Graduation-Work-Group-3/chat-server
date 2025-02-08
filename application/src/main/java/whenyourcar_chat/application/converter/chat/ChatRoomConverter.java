package whenyourcar_chat.application.converter.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import whenyourcar_chat.application.dto.chat.common.ChatCommonResponse;
import whenyourcar_chat.domain.entity.Room;
import whenyourcar_chat.domain.entity.User;
import whenyourcar_chat.domain.query.SearchRoomsQuery;

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

    public Page<ChatCommonResponse.ChatRoomResponseDto> toChatCommonResponse(Page<SearchRoomsQuery> searchRoomsQueries) {
        List<ChatCommonResponse.ChatRoomResponseDto> chatRoomResponses = searchRoomsQueries.getContent().stream()
                .map(room -> ChatCommonResponse.ChatRoomResponseDto.builder()
                        .roomId(room.getRoomId())
                        .user2Id(room.getUser2Id())
                        .picture(room.getPicture())
                        .name(room.getName())
                        .build())
                .collect(Collectors.toList());
        return new PageImpl<>(chatRoomResponses, searchRoomsQueries.getPageable(), searchRoomsQueries.getTotalElements());
    }
}
