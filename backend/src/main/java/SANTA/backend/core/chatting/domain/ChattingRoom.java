package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChattingRoom {

    private Long id;

    private String title;

    private String subTitle;

    private String clientUrl;

    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

}
