package whenyourcar.presentation.facade;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar.domain.chat.dto.common.ChatCommonResponse;
import whenyourcar.domain.chat.service.ChatMessageService;
import whenyourcar.domain.chat.service.ChatRoomService;
import whenyourcar.domain.user.dto.security.SessionUser;
import whenyourcar.domain.user.service.UserCommonService;

@Service
@RequiredArgsConstructor
public class ChatCommonFacade {
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final UserCommonService userCommonService;
    public void postChatRoom(Long userId, HttpSession httpSession) {
        Long user1Id = userCommonService.getUserId(httpSession);
        Long user2Id = userCommonService.verifyUserId(userId);

        chatMessageService.createMessageQueueForUser(user1Id);
        chatMessageService.createMessageQueueForUser(user2Id);
    }

    public Page<ChatCommonResponse.ChatRoomResponse> getChatRooms(Pageable pageable,
                                                                  HttpSession httpSession) {
        Long userId = userCommonService.getUserId(httpSession);
        return chatRoomService.getChatRooms(pageable, userId);
    }
}
