package whenyourcar.domain.chat.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import whenyourcar.storage.mysql.data.entity.Chat;
import whenyourcar.storage.mysql.data.query.SearchChatsQuery;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class ChatMessageResponseSet{
        private Long roomId;
        private String senderPicture;
        private String senderName;
        private Page<SearchChatsQuery> chatMessages;
    }

}
