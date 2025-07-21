package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class ChattingRoomMessage {
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChattingRoomUserEntity chattingRoomUser;
}
