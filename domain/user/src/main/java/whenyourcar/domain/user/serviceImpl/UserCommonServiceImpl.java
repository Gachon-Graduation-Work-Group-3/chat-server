package whenyourcar.domain.user.serviceImpl;

import code.exception.AuthenticationException;
import code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
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
    public Long getUserId(HttpServletRequest request) {
        String email = request.getHeader("X-User-Email");
        User user = userCommonRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user.getId();
    }

    @Override
    public Long getUserId(ServerHttpRequest request) {
        String email = request.getHeaders().getFirst("X-User-Email");
        User user = userCommonRepository.findByEmail(email)
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
