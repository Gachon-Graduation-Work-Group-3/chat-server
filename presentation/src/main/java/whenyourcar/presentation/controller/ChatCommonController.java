package whenyourcar.presentation.controller;

import code.status.SuccessStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import whenyourcar.domain.chat.dto.ChatCommonResponse;
import whenyourcar.domain.user.dto.security.SessionUser;
import whenyourcar.presentation.apiPayload.ApiResponse;
import whenyourcar.presentation.facade.ChatCommonFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatCommonController {
    private final HttpSession httpSession;
    private final ChatCommonFacade chatCommonFacade;
    @PostMapping("/")
    public ApiResponse<Void> postChatRoom(@RequestParam Long user2Id)  {
        chatCommonFacade.postChatRoom(user2Id, httpSession);
        return ApiResponse.onSuccess(SuccessStatus.CHAT_ROOM_CREATE_SUCCESS, null);
    }

    @GetMapping("/")
    public ApiResponse<Page<ChatCommonResponse.ChatRoomResponse>> getChatRooms(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_SEARCH_ROOM_SUCCESS, chatCommonFacade.getChatRooms( PageRequest.of(page, size), httpSession));
    }
}
