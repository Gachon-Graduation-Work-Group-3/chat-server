package whenyourcar.storage.rabbitmq.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class ChatMessageDto implements Serializable {
    private String message;
    private String messageId;
    private Long roomId;
    private Long sender;
    private String timestamp;
}
