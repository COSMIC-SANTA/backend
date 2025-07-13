package SANTA.backend.global.exception.type;

import SANTA.backend.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public DataNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public DataNotFoundException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public DataNotFoundException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
