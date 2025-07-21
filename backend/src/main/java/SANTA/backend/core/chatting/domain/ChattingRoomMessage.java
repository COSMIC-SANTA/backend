package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import lombok.Getter;

@Getter
public class ChattingRoomMessage {
    private Long id;

    private String message;

    private ChattingRoomUserEntity chattingRoomUser;
}
