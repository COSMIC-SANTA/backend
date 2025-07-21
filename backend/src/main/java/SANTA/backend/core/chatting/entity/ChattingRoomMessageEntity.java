package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "chatting_room_message")
public class ChattingRoomMessageEntity {

    @Id
    @GeneratedValue @Column(name = "chatting_room_message_id")
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_room_user_id")
    private ChattingRoomUserEntity chattingRoomUser;
}
