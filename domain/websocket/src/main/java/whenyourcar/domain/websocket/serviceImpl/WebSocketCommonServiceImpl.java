package whenyourcar.domain.websocket.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whenyourcar.domain.websocket.service.WebSocketCommonService;
import whenyourcar.storage.redis.serviceImpl.RedisStreamMessagePublisher;
import whenyourcar.storage.mysql.data.query.SearchRoomUsersQuery;
import whenyourcar.storage.mysql.repository.chat.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class WebSocketCommonServiceImpl implements WebSocketCommonService {
    private final ChatRoomRepository chatRoomRepository;
    private final RedisStreamMessagePublisher redisStreamMessagePublisher;
    @Override
    public void postMessageToRedisStream(Long roomId, Long sender, String message) {
        SearchRoomUsersQuery searchRoomUsersQuery = chatRoomRepository.findUsersByRoomId(roomId);
        redisStreamMessagePublisher.publishMessage(
                roomId,
                sender,
                searchRoomUsersQuery.getUser1Id(),
                searchRoomUsersQuery.getUser2Id(),
                message);
    }
}
