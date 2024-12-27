package whenyourcar.mongo.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collation = "chat")
@Builder
@AllArgsConstructor
@Getter
public class ChatMessage {
    @Id
    private String id;
    private Long roomId;
    private Long senderId;
    private String message;
    private LocalDateTime timestamp;
}
