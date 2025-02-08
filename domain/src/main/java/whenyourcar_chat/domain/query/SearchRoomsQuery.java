package whenyourcar_chat.domain.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchRoomsQuery {
    private Long roomId;
    private Long user2Id;
    private String name;
    private String picture;
}



