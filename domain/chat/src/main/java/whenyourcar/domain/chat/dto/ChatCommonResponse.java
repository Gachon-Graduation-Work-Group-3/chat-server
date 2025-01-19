package whenyourcar.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import whenyourcar.storage.mysql.data.entity.User;

import java.util.Date;

public class ChatCommonResponse {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class ChatRoomResponse{
        private Long roomId;
        private Long user2Id;
        private String name;
        private String picture;
    }


}
