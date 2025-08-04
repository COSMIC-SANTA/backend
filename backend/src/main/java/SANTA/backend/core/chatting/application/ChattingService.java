package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.chatting.dto.ChattingRoomMessageDto;
import SANTA.backend.core.chatting.dto.ChattingRoomMessageResponseDto;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;
    private final UserService userService;
    private final ChattingSender chattingSender;

    @Transactional(readOnly = true)
    public List<ChattingRoomResponseDto> getChattingRoomList() {
        List<ChattingRoomEntity> chattingRooms = chattingRepository.findAllRooms();
        return chattingRooms.stream().map(ChattingRoomResponseDto::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<ChattingRoomResponseDto> getChattingRoomByName(String roomName) {
        List<ChattingRoomEntity> chattingRooms = chattingRepository.findByName(roomName);
        return chattingRooms.stream().map(ChattingRoomResponseDto::fromEntity).toList();
    }

    @Transactional
    public ChattingRoomEntity createChattingRoom(String title, @Nullable String subTitle) {
        ChattingRoomEntity chattingRoom = ChattingRoomEntity.createChattingRoom(title, subTitle);
        ChattingRoomEntity savedChattingRoom = chattingRepository.saveChattingRoom(chattingRoom);
        log.warn("만들어진 채팅방의 id ={}",savedChattingRoom.getId());
        return savedChattingRoom;
    }

    @Transactional
    public ChattingRoomUserEntity participateChattingRoom(Long chattingRoomId, Long myId){
        UserEntity user = userService.findEntityById(myId);
        ChattingRoomEntity chattingRoom = chattingRepository.findById(chattingRoomId)
                .orElseThrow(()-> new CustomException(ErrorCode.CHATTING_ROOM_NOT_FOUND));
        ChattingRoomUserEntity chattingRoomUser = ChattingRoomUserEntity.createChattingRoomUser(user,chattingRoom); //채팅방과 사용자를 이용해 채팅 참여자를 만들고 연관관계 매핑
        return chattingRepository.saveChattingRoomUser(chattingRoomUser);
    }

    @Transactional
    public void sendMessage(Long roomId, Long userId, String message){
        ChattingRoomUserEntity chattingRoomUser = chattingRepository.findChattingRoomUser(roomId,userId)
                .orElseThrow(()-> new CustomException(ErrorCode.CHATTING_ROOM_USER_NOT_FOUND));
        ChattingRoomMessageEntity chattingRoomMessage = ChattingRoomMessageEntity.createChattingRoomMessage(message,chattingRoomUser);
        ChattingRoomMessageDto chattingRoomMessageDto = ChattingRoomMessageDto.from(chattingRoomMessage);
        //Todo 채팅메시지를 Sender를 통해 subscriber들에게 publish
        chattingSender.sendMessage(roomId,chattingRoomMessageDto);
        chattingRepository.saveChattingRoomMessage(chattingRoomMessage);
    }

    @Transactional
    public List<ChattingRoomMessageResponseDto> getChattingRoomMessageByRoomId(Long roomId) {
        List<ChattingRoomMessageEntity> chattingRoomMessages = chattingRepository.getChattingRoomMessageByRoomId(roomId);
        return chattingRoomMessages.stream().map(ChattingRoomMessageResponseDto::from).toList();
    }
}
