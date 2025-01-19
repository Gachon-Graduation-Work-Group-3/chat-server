package whenyourcar.domain.websocket.handler;

import code.exception.AuthenticationException;
import code.status.ErrorStatus;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import whenyourcar.domain.user.dto.security.SessionUser;
import org.springframework.web.socket.WebSocketHandler;
import whenyourcar.storage.redis.manager.RedisStreamListenerManager;
import whenyourcar.storage.redis.service.RedisStreamService;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class HttpSessionHandshakeInterceptor extends org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession httpSession = getSession(request);
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        Long userId = sessionUser.getUserId();
        attributes.put("userId", userId);
        System.out.println(sessionUser.getEmail());
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