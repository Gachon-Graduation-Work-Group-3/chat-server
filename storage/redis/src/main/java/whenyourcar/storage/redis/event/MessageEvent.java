package whenyourcar.storage.redis.event;

import lombok.Builder;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class MessageEvent extends ApplicationEvent {
    private final String message;
    private final Long roomId;
    private final Long userId;
    private final Long sender;
    private final String timestamps;
    public  MessageEvent(Object source, String message, Long roomId, Long userId, Long sender, String timestamps) {
        super(source);
        this.message = message;
        this.roomId = roomId;
        this.userId = userId;
        this.sender = sender;
        this.timestamps = timestamps;
    }
}
