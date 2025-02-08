package whenyourcar_chat.application.dto.chat.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class ChatCommonResponse {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class ChatRoomResponseDto{
        private Long roomId;
        private Long user2Id;
        private String name;
        private String picture;
    }


}
