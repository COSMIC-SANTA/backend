package SANTA.backend.core.chatting.domain;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import SANTA.backend.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public class ChattingRoomUser {
    private Long id;

    private User user;

    private ChattingRoom chattingRoom;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private List<ChattingRoomMessage> chattingRoomMessages = new ArrayList<>();

    @Builder
    private ChattingRoomUser(Long id, User user, ChattingRoom chattingRoom, LocalDateTime createdAt, LocalDateTime modifiedAt,List<ChattingRoomMessage> chattingRoomMessages) {
        this.id = id;
        this.user = user;
        this.chattingRoom = chattingRoom;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.chattingRoomMessages = chattingRoomMessages;
    }

    public static ChattingRoomUser from(ChattingRoomUserEntity chattingRoomUserEntity){
        return ChattingRoomUser.builder()
                .id(chattingRoomUserEntity.getId())
                .user(User.fromEntity(chattingRoomUserEntity.getUserEntity()))
                .chattingRoom(ChattingRoom.from(chattingRoomUserEntity.getChattingRoomEntity()))
                .createdAt(chattingRoomUserEntity.getCreatedAt())
                .modifiedAt(chattingRoomUserEntity.getModifiedAt())
                .chattingRoomMessages(
                        Optional.ofNullable(chattingRoomUserEntity.getChattingRoomMessageEntities())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(ChattingRoomMessage::from)
                                .toList()
                )
                .build();
    }

    //연관관계 편의 메서드
    public static ChattingRoomUser createChattingRoomUser(User user, ChattingRoom chattingRoom){
        ChattingRoomUser chattingRoomUser = ChattingRoomUser.builder()
                .user(user)
                .chattingRoom(chattingRoom)
                .build();
        user.getChattingRoomUsers().add(chattingRoomUser);
        chattingRoom.getChattingRoomUsers().add(chattingRoomUser);
        return chattingRoomUser;
    }
}
