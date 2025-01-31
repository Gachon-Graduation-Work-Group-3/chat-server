package whenyourcar.presentation.controller;

import code.status.SuccessStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import whenyourcar.domain.chat.dto.common.ChatCommonResponse;
import whenyourcar.presentation.apiPayload.ApiResponse;
import whenyourcar.presentation.facade.ChatCommonFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatCommonController {
    private final ChatCommonFacade chatCommonFacade;
    @PostMapping("/")
    public ApiResponse<Void> postChatRoom(
            HttpServletRequest request,
            @RequestParam Long user2Id)  {
        chatCommonFacade.postChatRoom(user2Id, request);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_ROOM_CREATE_SUCCESS, null);
    }

    @GetMapping("/")
    public ApiResponse<Page<ChatCommonResponse.ChatRoomResponse>> getChatRooms(
            HttpServletRequest request,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_SEARCH_ROOM_SUCCESS, chatCommonFacade.getChatRooms( PageRequest.of(page, size), request));
    }
}
