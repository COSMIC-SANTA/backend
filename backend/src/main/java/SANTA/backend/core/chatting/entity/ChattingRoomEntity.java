package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "chatting_room")
public class ChattingRoomEntity {

    @Id @GeneratedValue @JoinColumn(name = "chatting_room_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "client_url")
    private String clientUrl;

    @OneToMany(mappedBy = "chattingRoomEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

}
