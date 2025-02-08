package whenyourcar_chat.application.facade;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar_chat.application.dto.chat.common.ChatCommonResponse;
import whenyourcar_chat.application.service.chat.ChatMessageService;
import whenyourcar_chat.application.service.chat.ChatRoomService;
import whenyourcar_chat.application.service.user.UserCommonService;
import whenyourcar_chat.domain.entity.User;

@Service
@RequiredArgsConstructor
public class ChatCommonFacade {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserCommonService userCommonService;
    public void postChatRoom(Long userId, String email) {
        User user1 = userCommonService.getUserByEmail(email);
        User user2 = userCommonService.getUserById(userId);

        chatRoomService.createChatRoom(user1, user2);

        chatMessageService.createMessageQueueForUser(user1.getId());
        chatMessageService.createMessageQueueForUser(user2.getId());
    }

    public Page<ChatCommonResponse.ChatRoomResponseDto> getChatRooms(Pageable pageable,
                                                                     String email) {
        User user = userCommonService.getUserByEmail(email);
        return chatRoomService.getChatRooms(pageable, user);
    }
}
