package whenyourcar.storage.mysql.repository.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import whenyourcar.storage.mysql.data.entity.Room;
import whenyourcar.storage.mysql.data.query.SearchRoomUsersQuery;
import whenyourcar.storage.mysql.data.query.SearchRoomsQuery;

@Repository
public interface ChatRoomRepository extends JpaRepository<Room, Long> {
    @Query("select new whenyourcar.storage.mysql.data.query.SearchRoomsQuery(" +
            "   r.id, " +
            "   case when r.user1.id = :userId then r.user2.id " +
            "        when r.user2.id = :userId then r.user1.id end, " +
            "   case when r.user1.id = :userId then r.user2.picture " +
            "        when r.user2.id = :userId then r.user1.picture end," +
            "   case when r.user1.id = :userId then r.user2.name " +
            "        when r.user2.id = :userId then r.user1.name end) " +
            "from Room r " +
            "where r.user1.id = :userId or r.user2.id = :userId")
    Page<SearchRoomsQuery> findRoomsByUserId(Pageable pageable,
                                             @Param("userId") Long userId);

    @Query("select new whenyourcar.storage.mysql.data.query.SearchRoomUsersQuery(" +
            "r.user1.id, r.user2.id)" +
            "from Room r " +
            "where r.id = :roomId")
    SearchRoomUsersQuery findUsersByRoomId(@Param("roomId") Long roomId);
}
