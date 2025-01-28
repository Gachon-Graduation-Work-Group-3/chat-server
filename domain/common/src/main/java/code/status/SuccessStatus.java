package code.status;

import code.BaseCode;
import code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    CHAT_ROOM_CREATE_SUCCESS(HttpStatus.OK, "CHAT2000", "create chat room success"),
    CHAT_SEARCH_ROOM_SUCCESS(HttpStatus.OK, "CHAT2001", "search chat room success"),

    CHAT_SEARCH_MESSAGE_SUCCESS(HttpStatus.OK, "MESSAGE2001", "search chat message success"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
