package whenyourcar.presentation.controller;

import code.status.SuccessStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whenyourcar.domain.chat.dto.message.ChatMessageResponse;
import whenyourcar.presentation.apiPayload.ApiResponse;
import whenyourcar.presentation.facade.ChatMessageFacade;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/message")
public class ChatMessageController {
    private final ChatMessageFacade chatMessageFacade;
    @GetMapping("/")
    public ApiResponse<ChatMessageResponse.ChatMessageResponseSet> getChatMessage(
            HttpServletRequest request,
            @RequestParam Long roomId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return ApiResponse.onSuccess(SuccessStatus.CHAT_SEARCH_MESSAGE_SUCCESS, chatMessageFacade.getChatMessage(request, roomId, PageRequest.of(page, size)));
    }
}
