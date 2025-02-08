package whenyourcar_chat.presentation.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import whenyourcar_chat.application.dto.chat.common.ChatCommonResponse;
import whenyourcar_chat.application.facade.ChatCommonFacade;
import whenyourcar_chat.common.code.status.SuccessStatus;
import whenyourcar_chat.presentation.apiPayload.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class ChatCommonController {
    private final ChatCommonFacade chatCommonFacade;
    @PostMapping("/")
    public ApiResponse<Void> postChatRoom(
            HttpServletRequest httpServletRequest,
            @RequestParam Long user2Id)  {
        String email = httpServletRequest.getHeader("X-User-Email");
        chatCommonFacade.postChatRoom(user2Id, email);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_ROOM_CREATE_SUCCESS, null);
    }

    @GetMapping("/")
    public ApiResponse<Page<ChatCommonResponse.ChatRoomResponseDto>> getChatRooms(
            HttpServletRequest httpServletRequest,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        String email = httpServletRequest.getHeader("X-User-Email");
        return ApiResponse.onSuccess(SuccessStatus.CHAT_SEARCH_ROOM_SUCCESS, chatCommonFacade.getChatRooms( PageRequest.of(page, size), email));
    }
}
