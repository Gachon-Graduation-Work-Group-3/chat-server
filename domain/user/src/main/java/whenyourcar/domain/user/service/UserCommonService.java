package whenyourcar.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import whenyourcar.domain.user.dto.security.SessionUser;
import jakarta.servlet.http.HttpSession;
import whenyourcar.storage.mysql.data.entity.User;

public interface UserCommonService {
    public Long getUserId(HttpServletRequest request);

    public Long getUserId(ServerHttpRequest request);

    public Long verifyUserId(Long userId);
}
