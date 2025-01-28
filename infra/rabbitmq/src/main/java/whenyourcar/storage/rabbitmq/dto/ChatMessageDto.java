package whenyourcar.storage.rabbitmq.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class ChatMessageDto implements Serializable {
    private String id;
    private String message;
    private Long roomId;
    private Long sender;
}
