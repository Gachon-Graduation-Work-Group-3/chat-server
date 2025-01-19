package whenyourcar.presentation.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whenyourcar.domain.websocket.service.WebSocketCommonService;

@Service
@RequiredArgsConstructor
public class ChatBrokerFacade {
    private final WebSocketCommonService webSocketCommonService;

    public void postMessageToRedisStream(Long roomId,
                                         Long sender,
                                         String message) {
        webSocketCommonService.postMessageToRedisStream(roomId, sender, message);
    }


}
