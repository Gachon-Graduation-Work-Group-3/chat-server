package whenyourcar_chat.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRoomUsersQuery {
    private Long user1Id;
    private Long user2Id;
}
