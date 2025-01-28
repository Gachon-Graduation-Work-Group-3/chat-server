package whenyourcar.storage.mysql.data.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class SearchRoomsQuery {
    private Long roomId;
    private Long user2Id;
    private String name;
    private String picture;
}



