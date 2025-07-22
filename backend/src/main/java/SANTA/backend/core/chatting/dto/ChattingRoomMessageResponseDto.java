package SANTA.backend.core.chatting.dto;

import SANTA.backend.core.chatting.domain.ChattingRoomMessage;

import java.time.LocalDateTime;

public record ChattingRoomMessageResponseDto(
        LocalDateTime createdAt,
        String userName,
        String message
) {
    public static ChattingRoomMessageResponseDto from(ChattingRoomMessage chattingRoomMessage){
        return new ChattingRoomMessageResponseDto(chattingRoomMessage.getCreatedAt(),chattingRoomMessage.getChattingRoomUser().getUser().getNickname(), chattingRoomMessage.getMessage());
    }
}
