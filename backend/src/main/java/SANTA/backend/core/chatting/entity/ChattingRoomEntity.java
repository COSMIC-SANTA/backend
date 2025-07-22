package SANTA.backend.core.chatting.entity;

import SANTA.backend.core.chatting.domain.ChattingRoom;
import SANTA.backend.core.chatting.domain.ChattingRoomUser;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Entity
@Table(name = "chatting_room")
@NoArgsConstructor
public class ChattingRoomEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @JoinColumn(name = "chatting_room_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "client_url")
    private String clientUrl;

    @Column(name = "user_num")
    private Integer userNum;

    @OneToMany(mappedBy = "chattingRoomEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

    @Builder
    private ChattingRoomEntity(Long id, String title, String subTitle, String clientUrl, Integer userNum, List<ChattingRoomUserEntity> chattingRoomUserEntities) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.clientUrl = clientUrl;
        this.userNum = userNum;
        this.chattingRoomUserEntities = chattingRoomUserEntities;
    }

    public static ChattingRoomEntity from (ChattingRoom chattingRoom){
        return ChattingRoomEntity.builder()
                .title(chattingRoom.getTitle())
                .subTitle(chattingRoom.getSubTitle())
                .clientUrl(chattingRoom.getClientUrl())
                .userNum(chattingRoom.getUserNum())
                .chattingRoomUserEntities(
                        Optional.ofNullable(chattingRoom.getChattingRoomUsers())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(ChattingRoomUserEntity::from)
                                .toList()
                )
                .build();
    }
}
