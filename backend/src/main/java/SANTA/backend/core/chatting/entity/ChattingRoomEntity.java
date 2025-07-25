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

    @Column(name = "client_bawe_url")
    private String clienBasetUrl;

    @Column(name = "user_num")
    private Integer userNum;

    @OneToMany(mappedBy = "chattingRoomEntity", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChattingRoomUserEntity> chattingRoomUserEntities = new ArrayList<>();

    @Builder
    public ChattingRoomEntity(Long id, String title, String subTitle, String clientBaseUrl, Integer userNum, List<ChattingRoomUserEntity> chattingRoomUserEntities) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.clienBasetUrl = clientBaseUrl;
        this.userNum = userNum;
        this.chattingRoomUserEntities = chattingRoomUserEntities;
    }

    public static ChattingRoomEntity createChattingRoom(String title, String subTitle){
        return ChattingRoomEntity.builder()
                .title(title)
                .subTitle(subTitle)
                .userNum(0)
                .chattingRoomUserEntities(new ArrayList<>())
                .clientBaseUrl("/getMessage/")
                .build();
    }

    public String getClientUrl(){
        return this.clienBasetUrl+id;
    }
}
