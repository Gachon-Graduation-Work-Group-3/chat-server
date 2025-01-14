package whenyourcar.redis.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MessageEvent extends ApplicationEvent {
    private final String message;
    private final String messageId;
    private final String groupName;
    private final Long roomId;
    public MessageEvent(Object source, String message, String messageId, String groupName, Long roomId) {
        super(source);
        this.message = message;
        this.messageId = messageId;
        this.groupName = groupName;
        this.roomId = roomId;
    }
}
