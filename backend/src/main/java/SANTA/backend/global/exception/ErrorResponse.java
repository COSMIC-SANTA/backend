package SANTA.backend.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private String message;

    private String additionalMessage;

    private List<FieldError> errors;

    private String code;

    private ErrorResponse(ErrorCode code, List<FieldError> errors, String additionalMessage){
        this.message = code.getMessage();
        this.additionalMessage = additionalMessage;
        this.errors = errors;
        this.code = code.getCode();
    }

    private ErrorResponse(ErrorCode code, String additionalMessage){
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
        this.code = code.getCode();
        this.additionalMessage = additionalMessage;
    }

    public static ErrorResponse of(ErrorCode code, List<FieldError> errors,String additionalMessage){
        return new ErrorResponse(code,errors,additionalMessage);
    }

    public static ErrorResponse of(ErrorCode code, String additionalMessage){
        return new ErrorResponse(code, additionalMessage);
    }

    public static ErrorResponse of(ErrorCode code){
        return new ErrorResponse(code, null);
    }

}
