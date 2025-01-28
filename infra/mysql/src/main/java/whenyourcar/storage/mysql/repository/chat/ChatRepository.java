package whenyourcar.storage.mysql.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import whenyourcar.storage.mysql.data.entity.Chat;
import whenyourcar.storage.mysql.data.query.SearchChatsQuery;

public interface ChatRepository extends JpaRepository<Chat, String> {
    @Query("select new whenyourcar.storage.mysql.data.query.SearchChatsQuery(" +
            "c.id, c.message, c.user.id, c.createdAt)" +
            "from Chat c " +
            "where c.room.id = :roomId")
    Page<SearchChatsQuery> findChatByRoomId(Pageable pageable,  @Param("roomId") Long roomId);
}
