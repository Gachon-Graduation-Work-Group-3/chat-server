package whenyourcar_chat.common.handler;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import whenyourcar_chat.common.code.exception.AuthenticationException;
import whenyourcar_chat.common.code.status.ErrorStatus;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpSessionHandshakeInterceptor extends org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception ex) {
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest serverRequest) {
            return serverRequest.getServletRequest().getSession(false);
        }
        throw new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST);
    }
}