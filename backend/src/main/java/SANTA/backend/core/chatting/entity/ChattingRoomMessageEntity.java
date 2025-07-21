package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chatting_message")
public class ChattingMessageEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChattingRoomEntity chattingRoom;

    private
}
