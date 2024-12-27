package whenyourcar.websocket.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import whenyourcar.websocket.manager.WebSessionManager;
import whenyourcar.websocket.service.ChatMessageListener;
import whenyourcar.websocket.service.ChatMessagePublisher;


@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private static final String STREAM_KEY_PREFIX = "chat:room:";
    private final ChatMessageListener chatMessageListener;
    private final ChatMessagePublisher chatMessagePublisher;
    private final WebSessionManager webSessionManager;


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)  {
        Long roomId = webSessionManager.getSessionAttribute(session, "roomId");
        Long userId = webSessionManager.getSessionAttribute(session, "userId");
        String userMessage = message.getPayload();

        chatMessagePublisher.publishMessage(userMessage, STREAM_KEY_PREFIX+roomId, roomId, userId);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long roomId = webSessionManager.getSessionAttribute(session, "roomId");
        Long userId = webSessionManager.getSessionAttribute(session, "userId");

        webSessionManager.addWebSocketSession(session, roomId, userId);

        new Thread(() -> chatMessageListener.listenToStream(STREAM_KEY_PREFIX+roomId, roomId)).start();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long roomId = webSessionManager.getSessionAttribute(session, "roomId");
        Long userId = webSessionManager.getSessionAttribute(session, "userId");

        webSessionManager.deleteWebSocketSession(roomId, userId);
    }
}
