package whenyourcar_chat.common.code.exception;


import whenyourcar_chat.common.code.BaseErrorCode;
import whenyourcar_chat.common.code.status.ErrorStatus;

public class AuthenticationException extends RuntimeException {
    private BaseErrorCode code;

    public AuthenticationException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorStatus getErrorCode() {
        return (ErrorStatus)code;
    }
}
