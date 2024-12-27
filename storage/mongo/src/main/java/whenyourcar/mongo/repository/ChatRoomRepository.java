package whenyourcar.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import whenyourcar.mongo.data.ChatRoom;

import java.util.Optional;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    Optional<ChatRoom> findByUser1IdAndUser2Id(Long user1Id, Long user2Id);
}
