package SANTA.backend.core.chatting.api;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.chatting.application.ChattingService;
import SANTA.backend.core.chatting.application.LettuceLockStockFacade;
import SANTA.backend.core.chatting.dto.ChattingRoomMessageResponseDto;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import SANTA.backend.core.chatting.dto.MessageDto;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/community/chatting")
public class ChattingApi {

    private final ChattingService chattingService;

    private final LettuceLockStockFacade lettuceLockStockFacade;

    @GetMapping
    public ResponseEntity<ResponseHandler<List<ChattingRoomResponseDto>>> chattingRoomList() {
        List<ChattingRoomResponseDto> chattingRoomLists = chattingService.getChattingRoomList();
        return ResponseEntity.ok().body(ResponseHandler.success(chattingRoomLists));
    }

    @GetMapping("/{roomName}")
    public ResponseEntity<ResponseHandler<List<ChattingRoomResponseDto>>> searchChattingRoom(@PathVariable("roomName") String roomName) {
        List<ChattingRoomResponseDto> chattingRooms = chattingService.getChattingRoomByName(roomName);
        return ResponseEntity.ok().body(ResponseHandler.success(chattingRooms));
    }


    @PostMapping
    public ResponseEntity<ResponseHandler<Boolean>> participateChattingRoom(@RequestParam("roomId")Long roomId, @AuthenticationPrincipal CustomUserDetails userDetails) throws InterruptedException {
        Long userId = userDetails.getUserId();
        lettuceLockStockFacade.participateChattingRoom(roomId,userId);
        return ResponseEntity.ok().body(ResponseHandler.success(true));
    }

    @MessageMapping("/chat")
    public void sendMessage(@RequestBody MessageDto token) {
        Long roomId = token.roomId();
        Long userId = token.userId();
        String message = token.message();
        chattingService.sendMessage(roomId, userId, message);
    }

    @GetMapping("{roomId}")
    public ResponseEntity<ResponseHandler<List<ChattingRoomMessageResponseDto>>> getChattings(@PathVariable("roomId") Long roomId) {
        List<ChattingRoomMessageResponseDto> chattingRoomMessageResponseDtos = chattingService.getChattingRoomMessageByRoomId(roomId);
        return ResponseEntity.ok().body(ResponseHandler.success(chattingRoomMessageResponseDtos));
    }

}
