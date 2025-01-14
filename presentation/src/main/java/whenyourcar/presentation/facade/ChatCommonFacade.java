package whenyourcar.presentation.facade;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar.domain.chat.dto.ChatCommonResponse;
import whenyourcar.domain.chat.service.ChatCommonService;
import whenyourcar.domain.user.dto.security.SessionUser;
import whenyourcar.domain.user.service.UserCommonService;

@Service
@RequiredArgsConstructor
public class ChatCommonFacade {
    private final ChatCommonService chatCommonService;
    public void postChatRoom(Long user2Id, HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        chatCommonService.postChatRoom(sessionUser.getUserId(), user2Id);
    }

    public Page<ChatCommonResponse.ChatRoomResponse> getChatRooms(Pageable pageable,
                                               HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        return chatCommonService.getChatRooms(pageable, sessionUser.getUserId());
    }
}
