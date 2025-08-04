package SANTA.backend.global.common;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseHandler<T> {
    private String message;
    private T data;

    @Builder
    private ResponseHandler(String message, T data){
        this.message = message;
        this.data =data;
    }

    public static <T> ResponseHandler<T> success(T data){
        return ResponseHandler.<T>builder()
                .message("success")
                .data(data)
                .build();
    }

    public static <T> ResponseHandler<T> fail(T data, String message){
        return ResponseHandler.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}
