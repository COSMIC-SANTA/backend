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

    private List<FieldError> errors;

    private String code;

    private ErrorResponse(ErrorCode code, List<FieldError> errors){
        this.message = code.getMessage();
        this.errors = errors;
        this.code = code.getCode();
    }

    private ErrorResponse(ErrorCode code){
        this.message = code.getMessage();
        this.errors = new ArrayList<>();
        this.code = code.getCode();
    }

    public static ErrorResponse of(ErrorCode code, List<FieldError> errors){
        return new ErrorResponse(code,errors);
    }

    public static ErrorResponse of(ErrorCode code){
        return new ErrorResponse(code);
    }

}
