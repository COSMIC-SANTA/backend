package SANTA.backend.global.exception.type;

import SANTA.backend.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException{
    private final ErrorCode errorCode;

    public ExternalApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ExternalApiException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ExternalApiException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
