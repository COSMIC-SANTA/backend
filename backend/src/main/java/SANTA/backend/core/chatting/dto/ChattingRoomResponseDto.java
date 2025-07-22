package SANTA.backend.core.chatting.dto;

import SANTA.backend.core.chatting.entity.ChattingRoomEntity;

public record ChattingRoomResponseDto(
        Long id,
        String chattingRoomName,
        Integer memberNum
) {

    public static ChattingRoomResponseDto fromEntity(ChattingRoomEntity chattingRoom){
        return new ChattingRoomResponseDto(chattingRoom.getId(), chattingRoom.getTitle(), chattingRoom.getUserNum());
    }
}
