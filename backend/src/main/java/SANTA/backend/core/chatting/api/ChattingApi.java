package SANTA.backend.core.chatting.api;

import SANTA.backend.core.chatting.application.ChattingService;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/community/chatting")
public class ChattingApi {

    private final ChattingService chattingService;

    @GetMapping
    public ResponseEntity<List<ChattingRoomResponseDto>> chattingRoomList(){
        List<ChattingRoomResponseDto> chattingRoomLists = chattingService.getChattingRoomList();
        return ResponseEntity.ok().body(chattingRoomLists);
    }

    @GetMapping("/{roomName}")
    public ResponseEntity<List<ChattingRoomResponseDto>> searchChattingRoom(@PathVariable("roomName")String roomName){
        List<ChattingRoomResponseDto> chattingRooms = chattingService.getChattingRoomByName(roomName);
        return ResponseEntity.ok().body(chattingRooms);
    }

    @MessageMapping("/{roomId}")
    public void sendMessage(@PathVariable("roomId")Long roomId, @ResponseBody String message){
        chattingService.sendMessage(roomId, message);
    }
}
