package SANTA.backend.core.chatting.dto;

import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;

import java.time.LocalDateTime;

public record ChattingRoomMessageResponseDto(
        LocalDateTime createdAt,
        String userName,
        String message
) {
    public static ChattingRoomMessageResponseDto from(ChattingRoomMessageEntity chattingRoomMessage){
        return new ChattingRoomMessageResponseDto(chattingRoomMessage.getCreatedAt(),chattingRoomMessage.getChattingRoomUser().getUserEntity().getNickname(), chattingRoomMessage.getMessage());
    }
}
