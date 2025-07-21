package SANTA.backend.core.chatting.entity;

import SANTA.backend.core.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "chatting_room_user")
public class ChattingRoomUserEntity {

    @Id @GeneratedValue @Column(name = "chatting_room_user_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "chatting_room_id")
    ChattingRoomEntity chattingRoom;
}
