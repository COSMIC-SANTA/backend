package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chatting_room_message")
public class ChattingRoomMessageEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChattingRoomUserEntity chattingRoomUser;
}
