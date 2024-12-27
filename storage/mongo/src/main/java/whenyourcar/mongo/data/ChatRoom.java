package whenyourcar.mongo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat_room")
@Builder
@AllArgsConstructor
@Getter
public class ChatRoom {
    @Id
    private String id; // MongoDB의 ObjectId가 기본적으로 String
    private Long user1Id; // 첫 번째 사용자 ID
    private Long user2Id; // 두 번째 사용자 ID
}