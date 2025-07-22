package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChattingRoomMessage {
    private Long id;

    private String message;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private ChattingRoomUser chattingRoomUser;

    @Builder
    private ChattingRoomMessage(Long id, String message, LocalDateTime createdAt, LocalDateTime modifiedAt, ChattingRoomUser chattingRoomUser){
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.chattingRoomUser = chattingRoomUser;
    }

    @Builder
    public static ChattingRoomMessage from (ChattingRoomMessageEntity chattingRoomMessageEntity){
        return ChattingRoomMessage.builder()
                .id(chattingRoomMessageEntity.getId())
                .message(chattingRoomMessageEntity.getMessage())
                .createdAt(chattingRoomMessageEntity.getCreatedAt())
                .modifiedAt(chattingRoomMessageEntity.getModifiedAt())
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
