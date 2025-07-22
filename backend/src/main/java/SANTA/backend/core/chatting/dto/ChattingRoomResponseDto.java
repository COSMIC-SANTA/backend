package SANTA.backend.core.chatting.dto;

import SANTA.backend.core.chatting.domain.ChattingRoom;

public record ChattingRoomResponseDto(
        Long id,
        String chattingRoomName,
        Integer memberNum
) {

    public static ChattingRoomResponseDto fromDomain(ChattingRoom chattingRoom){
        return new ChattingRoomResponseDto(chattingRoom.getId(), chattingRoom.getTitle(), chattingRoom.getUserNum());
    }
}
