package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChattingRoomMessage {
    private Long id;

    private String message;

    private ChattingRoomUser chattingRoomUser;

    @Builder
    private ChattingRoomMessage(Long id, String message, ChattingRoomUser chattingRoomUser){
        this.id = id;
        this.message = message;
        this.chattingRoomUser = chattingRoomUser;
    }

    @Builder
    public static ChattingRoomMessage from (ChattingRoomMessageEntity chattingRoomMessageEntity){
        return ChattingRoomMessage.builder()
                .id(chattingRoomMessageEntity.getId())
                .message(chattingRoomMessageEntity.getMessage())
                .chattingRoomUser(ChattingRoomUser.from(chattingRoomMessageEntity.getChattingRoomUser()))
                .build();
    }

    public static ChattingRoomMessage createChattingRoomMessage(String message, ChattingRoomUser chattingRoomUser){
        ChattingRoomMessage chattingRoomMessage = ChattingRoomMessage.builder()
                .message(message)
                .chattingRoomUser(chattingRoomUser)
                .build();
        chattingRoomUser.getChattingRoomMessages().add(chattingRoomMessage);
        chattingRoomMessage.chattingRoomUser = chattingRoomUser;
        return chattingRoomMessage;
    }
}
