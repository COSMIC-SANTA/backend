package SANTA.backend.core.chatting.entity;

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

    @Transient
    private static String clientBaseUrl = "/spring/getMessage/";

    @OneToMany(mappedBy = "chattingRoomEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

    @Builder
    public ChattingRoomEntity(Long id, String title, String subTitle, String clientUrl, Integer userNum, List<ChattingRoomUserEntity> chattingRoomUserEntities) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.clientUrl = clientUrl;
        this.userNum = userNum;
        this.chattingRoomUserEntities = chattingRoomUserEntities;
    }

    public static ChattingRoomEntity createChattingRoom(String title, String subTitle){
        return ChattingRoomEntity.builder()
                .title(title)
                .subTitle(subTitle)
                .userNum(0)
                .chattingRoomUserEntities(new ArrayList<>())
                .build();
    }

    public String getClientUrl(){
        return clientBaseUrl+id;
    }
}
