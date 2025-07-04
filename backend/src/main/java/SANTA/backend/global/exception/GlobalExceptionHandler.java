package SANTA.backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 처음엔, 최상위 예외로 다 처리하도록 설정했습니다. 개발하며 따로 처리가 필요한 예외들을 추가해주세요
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        log.info("ExceptionHandler에 잡힌 예외 {}",e.getMessage());
        ErrorResponse response =ErrorResponse.of(ErrorCode.TEMPORARY_ERROR);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
