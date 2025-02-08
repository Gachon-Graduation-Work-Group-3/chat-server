package whenyourcar_chat.common.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebSocketHandler implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (headerAccessor.getCommand() == null) return message;
        switch (headerAccessor.getCommand()) {
            case DISCONNECT: {
                System.out.println("Stop Listening");
            }
            default: {
                System.out.println(headerAccessor.getCommand());
                System.out.println("Destination: " + headerAccessor.getDestination());
                System.out.println("Destination: " + headerAccessor.getUser());
                break;
            }
        }
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (headerAccessor.getCommand() == null) return;
        switch (headerAccessor.getCommand()) {
            case SUBSCRIBE: {
                System.out.println("STOMP Listening Start");
            }
            case CONNECT:{
            }
        }

    }
}
