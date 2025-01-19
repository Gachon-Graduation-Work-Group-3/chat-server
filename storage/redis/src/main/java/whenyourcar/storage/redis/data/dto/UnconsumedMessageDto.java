package whenyourcar.storage.redis.data.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class UnconsumedMessageDto {
    @Builder
    @Getter
    public static class UnconsumedMessages {
        private List<UnconsumedMessagePerRoom> unconsumedMessages;
    }

    @Builder
    @Getter
    public static class UnconsumedMessagePerRoom {
        private Long roomId;
        private List<UnconsumedMessageDetail> unconsumedMessageDetails;
    }

    @Builder
    @Getter
    public static class UnconsumedMessageDetail {
        private String message;
        private Long sender;
        private String timestamp;
    }
}
