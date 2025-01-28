package whenyourcar.domain.user.service;

import whenyourcar.domain.user.dto.security.SessionUser;
import jakarta.servlet.http.HttpSession;
import whenyourcar.storage.mysql.data.entity.User;

public interface UserCommonService {
    public SessionUser getSessionUser(HttpSession httpSession);
    public Long getUserId(HttpSession httpSession);

    public Long verifyUserId(Long userId);
}
