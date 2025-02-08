package whenyourcar_chat.application.service.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import whenyourcar_chat.domain.entity.User;

public interface UserCommonService {
    public Long getUserIdByEmail(String email);
    public User getUserByEmail(String email);
    public User getUserById(Long userId);
    public Long verifyUserId(Long userId);
}
