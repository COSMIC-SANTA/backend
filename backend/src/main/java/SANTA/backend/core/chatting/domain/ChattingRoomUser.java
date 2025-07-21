package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomEntity;
import SANTA.backend.core.user.entity.UserEntity;
import lombok.Getter;

@Getter
public class ChattingRoomUser {
    Long id;

    UserEntity user;

    ChattingRoomEntity chatting;
}
