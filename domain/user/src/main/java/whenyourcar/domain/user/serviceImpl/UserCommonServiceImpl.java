package whenyourcar.domain.user.serviceImpl;

import code.exception.AuthenticationException;
import code.status.ErrorStatus;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whenyourcar.domain.user.dto.security.SessionUser;
import whenyourcar.domain.user.service.UserCommonService;
import whenyourcar.storage.mysql.data.entity.User;
import whenyourcar.storage.mysql.repository.user.UserCommonRepository;

@Service
@RequiredArgsConstructor
public class UserCommonServiceImpl implements UserCommonService {
    private final UserCommonRepository userCommonRepository;
    @Override
    public SessionUser getSessionUser(HttpSession httpSession)  {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            throw new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST);
        }
        return user;
    }

    @Override
    public Long getUserId(HttpSession httpSession) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("user");
        User user = userCommonRepository.findUserById(sessionUser.getUserId())
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user.getId();
    }

    @Override
    public Long verifyUserId(Long userId) {
        User user = userCommonRepository.findUserById(userId)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user.getId();
    }
}
