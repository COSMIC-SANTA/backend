package SANTA.backend.core.user.entity;

import SANTA.backend.core.chatting.entity.ChattingRoomUserEntity;
import SANTA.backend.core.group.entity.GroupUserEntity;
import SANTA.backend.core.user.domain.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter//Entity는 setter설정XXX
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupUserEntity> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChattingRoomUserEntity> chattingRoomUsers = new ArrayList<>();

    @Embedded
    @Column(name = "medal")
    Medal medal;

    @Enumerated(EnumType.STRING)
    @Column(name = "interest")
    private Interest interest;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Builder
    public UserEntity(Long userId, String username, String password, String nickname, int age, Role role, String location, Medal medal,
                      Interest interest, Level level) {
        this.id = userId;
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

    public static UserEntity from(User user) {
        return UserEntity.builder()
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
