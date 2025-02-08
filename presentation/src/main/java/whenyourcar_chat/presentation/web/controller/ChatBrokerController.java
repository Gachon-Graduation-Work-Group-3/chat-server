package whenyourcar_chat.presentation.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import whenyourcar_chat.application.facade.ChatBrokerFacade;

@Controller
@RequiredArgsConstructor
public class ChatBrokerController {
    private final ChatBrokerFacade chatBrokerFacade;
    @MessageMapping("/{roomId}/{sender}")
    public void postMessageToRedisStream(@DestinationVariable Long roomId,
                                         @DestinationVariable Long sender,
                                         String message) {
        chatBrokerFacade.sendMessageToQueue(roomId, sender, message);
    }
}
