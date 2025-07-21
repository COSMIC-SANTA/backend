package SANTA.backend.core.user.entity;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import SANTA.backend.core.group.entity.GroupUserEntity;
import SANTA.backend.core.user.domain.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter//Entity는 setter설정XXX
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private Long userId; //->이거 string형임

    private String username;

    private String password;

    private String nickname;

    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String location;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupUserEntity> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChattingRoomUserEntity> userChattings = new ArrayList<>();

    @Embedded
    Medal medal;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Builder
    public UserEntity(Long userId, String username, String password, String nickname, int age, Role role, String location, Medal medal, Interest interest, Level level) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.role = role;
        this.location = location;
        this.medal = medal;
        this.interest = interest;
        this.level = level;
    }

    public static UserEntity from(User user){
        return UserEntity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .age(user.getAge())
                .role(user.getRole())
                .location(user.getLocation())
                .medal(user.getMedal())
                .interest(user.getInterest())
                .level(user.getLevel())
                .build();
    }

}
