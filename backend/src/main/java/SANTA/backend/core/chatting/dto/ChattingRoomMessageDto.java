package SANTA.backend.core.chatting.dto;

import SANTA.backend.core.chatting.entity.ChattingRoomMessageEntity;

public record ChattingRoomMessageDto(
        String message,
        String clientUrl
) {

    public static ChattingRoomMessageDto from(ChattingRoomMessageEntity chattingRoomMessage){
        String message = chattingRoomMessage.getMessage();
        String clientUrl = chattingRoomMessage.getChattingRoomUser().getChattingRoomEntity().getClientUrl();
        return new ChattingRoomMessageDto(message,clientUrl);
    }
}
