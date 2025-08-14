package SANTA.backend.core.chatting.entity;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "chatting_room_user")
@NoArgsConstructor
public class ChattingRoomUserEntity extends BaseEntity {

    @Id @GeneratedValue @Column(name = "chatting_room_user_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "chatting_room_id")
    ChattingRoomEntity chattingRoomEntity;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<ChattingRoomMessageEntity> chattingRoomMessageEntities = new ArrayList<>();

    @Builder
    public ChattingRoomUserEntity(Long id, UserEntity userEntity, ChattingRoomEntity chattingRoomEntity, List<ChattingRoomMessageEntity> chattingRoomMessageEntities){
        this.id = id;
        this.userEntity = userEntity;
        this.chattingRoomEntity = chattingRoomEntity;
        this.chattingRoomMessageEntities = chattingRoomMessageEntities;
    }

    //연관관계 편의 메서드
    public static ChattingRoomUserEntity createChattingRoomUser(UserEntity userEntity, ChattingRoomEntity chattingRoom){
        ChattingRoomUserEntity chattingRoomUser = ChattingRoomUserEntity.builder()
                .userEntity(userEntity)
                .chattingRoomEntity(chattingRoom)
                .build();
        userEntity.getChattingRoomUsers().add(chattingRoomUser);
        chattingRoom.getChattingRoomUserEntities().add(chattingRoomUser);
        return chattingRoomUser;
    }

}
