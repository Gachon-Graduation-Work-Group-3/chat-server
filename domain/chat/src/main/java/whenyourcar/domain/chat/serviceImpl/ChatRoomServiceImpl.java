package whenyourcar.domain.chat.serviceImpl;

import code.exception.GeneralException;
import code.status.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import whenyourcar.domain.chat.converter.ChatRoomConverter;
import whenyourcar.domain.chat.dto.common.ChatCommonResponse;
import whenyourcar.domain.chat.service.ChatRoomService;
import whenyourcar.storage.mysql.repository.chat.ChatRepository;
import whenyourcar.storage.mysql.data.entity.User;
import whenyourcar.storage.mysql.repository.chat.ChatRoomRepository;
import whenyourcar.storage.mysql.repository.user.UserCommonRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final UserCommonRepository userCommonRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomConverter chatRoomConverter;

    @Override
    public Page<ChatCommonResponse.ChatRoomResponse> getChatRooms(Pageable pageable, Long userId) {
        User user1 = userCommonRepository.findUserById(userId)
                .orElseThrow(()-> new GeneralException(ErrorStatus.USER_IS_NOT_EXIST));

        return chatRoomConverter.toChatCommonResponse(chatRoomRepository.findPageRoomsByUserId(pageable, user1.getId()));

    }

    @Override
    public boolean isUserInRoom(Long roomId, Long userId) {
        return chatRoomRepository.existsRoomByUserId(roomId, userId);
    }

    @Override
    public void createChatRoom(Long user1Id, Long user2Id) {
        User user1 = userCommonRepository.findUserById(user1Id)
                .orElseThrow(()-> new GeneralException(ErrorStatus.USER_IS_NOT_EXIST));
        User user2 = userCommonRepository.findUserById(user2Id)
                .orElseThrow(()-> new GeneralException(ErrorStatus.USER_IS_NOT_EXIST));

        if(!chatRoomRepository.existsRoomByUsers(user1.getId(), user2.getId())) {
            chatRoomRepository.save(chatRoomConverter.toChatRoom(user1, user2));
        }
    }
}
