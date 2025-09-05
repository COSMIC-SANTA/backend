package SANTA.backend.global.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingApi {
    @GetMapping("/ping")
    public ResponseEntity<ResponseHandler<String>> ping(){
        return ResponseEntity.ok().body(ResponseHandler.success("pong"));
    }
}

