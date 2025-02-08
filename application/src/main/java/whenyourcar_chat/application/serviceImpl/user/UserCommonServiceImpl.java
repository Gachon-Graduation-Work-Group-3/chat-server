package whenyourcar_chat.application.serviceImpl.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whenyourcar_chat.application.service.user.UserCommonService;
import whenyourcar_chat.common.code.exception.AuthenticationException;
import whenyourcar_chat.common.code.status.ErrorStatus;
import whenyourcar_chat.domain.entity.User;
import whenyourcar_chat.domain.repository.user.UserCommonRepository;

@Service
@RequiredArgsConstructor
public class UserCommonServiceImpl implements UserCommonService {
    private final UserCommonRepository userCommonRepository;

    @Override
    public Long getUserIdByEmail(String email) {
        User user = userCommonRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user.getId();
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userCommonRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user;
    }

    @Override
    public User getUserById(Long userId) {
        User user = userCommonRepository.findUserById(userId)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user;
    }

    @Override
    public Long verifyUserId(Long userId) {
        User user = userCommonRepository.findUserById(userId)
                .orElseThrow(() -> new AuthenticationException(ErrorStatus.USER_IS_NOT_EXIST));
        return user.getId();
    }
}
