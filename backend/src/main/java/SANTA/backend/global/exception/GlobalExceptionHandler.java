package SANTA.backend.global.exception;

import SANTA.backend.global.exception.type.DataNotFoundException;
import SANTA.backend.global.exception.type.ExternalApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 처음엔, 최상위 예외로 다 처리하도록 설정했습니다. 개발하며 따로 처리가 필요한 예외들을 추가해주세요
     */

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.warn("DataNotFoundExceptionHandler에 잡힌 예외 {} - {}", e.getErrorCode().getCode(), e.getMessage());
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getState()).body(response);
    }

    @ExceptionHandler(ExternalApiException.class)
    protected ResponseEntity<ErrorResponse> handleExternalApiException(ExternalApiException e) {
        log.info("ExternalApiExceptionHandler에 잡힌 예외 {} - {}", e.getErrorCode().getCode(), e.getMessage());
        ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getState()).body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.info("ExceptionHandler에 잡힌 예외 {}", e.getMessage());
        ErrorResponse response = ErrorResponse.of(ErrorCode.TEMPORARY_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
