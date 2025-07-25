package SANTA.backend.core.chatting.entity;

import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "chatting_room_message")
@NoArgsConstructor
public class ChattingRoomMessageEntity extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "chatting_room_message_id")
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatting_room_user_id")
    private ChattingRoomUserEntity chattingRoomUser;

    @Builder
    public ChattingRoomMessageEntity(Long id, String message, ChattingRoomUserEntity chattingRoomUserEntity) {
        this.id = id;
        this.message = message;
        this.chattingRoomUser = chattingRoomUserEntity;
    }

    public static ChattingRoomMessageEntity createChattingRoomMessage(String message, ChattingRoomUserEntity chattingRoomUser){
        ChattingRoomMessageEntity chattingRoomMessage = ChattingRoomMessageEntity.builder()
                .message(message)
                .chattingRoomUserEntity(chattingRoomUser)
                .build();
        chattingRoomUser.getChattingRoomMessageEntities().add(chattingRoomMessage);
        chattingRoomMessage.chattingRoomUser = chattingRoomUser;
        return chattingRoomMessage;
    }
}
