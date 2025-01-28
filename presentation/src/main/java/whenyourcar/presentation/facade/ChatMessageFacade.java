package whenyourcar.presentation.facade;

import code.exception.GeneralException;
import code.status.ErrorStatus;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar.domain.chat.dto.message.ChatMessageResponse;
import whenyourcar.domain.chat.service.ChatMessageService;
import whenyourcar.domain.chat.service.ChatRoomService;
import whenyourcar.domain.user.dto.security.SessionUser;
import whenyourcar.domain.user.service.UserCommonService;

@Service
@RequiredArgsConstructor
public class ChatMessageFacade {
    private final UserCommonService userCommonService;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    public ChatMessageResponse.ChatMessageResponseSet getChatMessage(HttpSession httpSession,
                                                                     Long roomId,
                                                                     Pageable pageable) {
        Long userId = userCommonService.getUserId(httpSession);
        if (chatRoomService.isUserInRoom(roomId, userId)) {
            return chatMessageService.getChat(pageable, roomId, userId);
        }else {
            throw new GeneralException(ErrorStatus.ROOM_IS_NOT_EXIST);
        }
    }

}
