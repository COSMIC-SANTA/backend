package SANTA.backend.core.chatting.application;

import SANTA.backend.core.chatting.domain.ChattingRepository;
import SANTA.backend.core.chatting.domain.ChattingRoom;
import SANTA.backend.core.chatting.domain.ChattingRoomMessage;
import SANTA.backend.core.chatting.domain.ChattingRoomUser;
import SANTA.backend.core.chatting.dto.ChattingRoomMessageResponseDto;
import SANTA.backend.core.chatting.dto.ChattingRoomResponseDto;
import SANTA.backend.core.user.application.UserService;
import SANTA.backend.core.user.domain.User;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ChattingService {

    private final TopicExchange chattingExchange;
    private final ChattingRepository chattingRepository;
    private final AmqpAdmin amqpAdmin;
    private final UserService userService;
    private final ChattingSender chattingSender;

    public ChattingService(@Qualifier("chattingExchange")TopicExchange chattingExchange, ChattingRepository chattingRepository, AmqpAdmin amqpAdmin, UserService userService, ChattingSender chattingSender) {
        this.chattingExchange = chattingExchange;
        this.chattingRepository = chattingRepository;
        this.amqpAdmin = amqpAdmin;
        this.userService = userService;
        this.chattingSender = chattingSender;
    }

    @Transactional(readOnly = true)
    public List<ChattingRoomResponseDto> getChattingRoomList() {
        List<ChattingRoom> chattingRooms = chattingRepository.findAllRooms();
        return chattingRooms.stream().map(ChattingRoomResponseDto::fromDomain).toList();
    }

    @Transactional(readOnly = true)
    public List<ChattingRoomResponseDto> getChattingRoomByName(String roomName) {
        List<ChattingRoom> chattingRooms = chattingRepository.findByName(roomName);
        return chattingRooms.stream().map(ChattingRoomResponseDto::fromDomain).toList();
    }

    @Transactional
    public ChattingRoom createChattingRoom(String title, @Nullable String subTitle) {
        ChattingRoom chattingRoom = ChattingRoom.createChattingRoom(title, subTitle);
        ChattingRoom savedChattingRoom = chattingRepository.saveChattingRoom(chattingRoom);
        log.warn("만들어진 채팅방의 id ={}",savedChattingRoom.getId());
        return savedChattingRoom;
    }

    @Transactional
    public ChattingRoomUser participateChattingRoom(Long chattingRoomId, Long myId){
        User user = userService.findById(myId);
        ChattingRoom chattingRoom = chattingRepository.findById(chattingRoomId);
        ChattingRoomUser chattingRoomUser = ChattingRoomUser.createChattingRoomUser(user,chattingRoom); //채팅방과 사용자를 이용해 채팅 참여자를 만들고 연관관계 매핑
        return chattingRepository.saveChattingRoomUser(chattingRoomUser);
    }

    @Transactional
    public void sendMessage(Long roomId, Long userId, String message){
        ChattingRoomUser chattingRoomUser = chattingRepository.findChattingRoomUser(roomId,userId);
        ChattingRoomMessage chattingRoomMessage = ChattingRoomMessage.createChattingRoomMessage(message,chattingRoomUser);
        //Todo 채팅메시지를 Sender를 통해 subscriber들에게 publish
        chattingSender.sendMessage(roomId,chattingRoomMessage);
        chattingRepository.saveChattingRoomMessage(chattingRoomMessage);
    }

    @Transactional
    public List<ChattingRoomMessageResponseDto> getChattingRoomMessageByRoomId(Long roomId) {
        List<ChattingRoomMessage> chattingRoomMessages = chattingRepository.getChattingRoomMessageByRoomId(roomId);
        return chattingRoomMessages.stream().map(ChattingRoomMessageResponseDto::from).toList();
    }
}
