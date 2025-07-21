package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "chatting_room")
public class ChattingRoomEntity {

    @Id @GeneratedValue
    private Long id;

    private String title;

    private String subTitle;

    private String clientUrl;

    @OneToMany
    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

}
