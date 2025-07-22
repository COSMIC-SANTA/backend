package SANTA.backend.core.user.domain;

import SANTA.backend.core.chatting.domain.ChattingRoomUser;
import SANTA.backend.core.group.domain.GroupUser;
import SANTA.backend.core.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class User {

    private Long userId;

    private String username;
    private String password;
    private String nickname;
    private int age;
    private Role role;
    private String location;
    private List<GroupUser> groupUsers;
    private List<ChattingRoomUser> chattingRoomUsers;
    private Medal medal;
    private Interest interest;
    private Level level;


    @Builder
    protected User(Long userId, String username, String password, String nickname, int age, Role role, String location, List<GroupUser> groupUsers, List<ChattingRoomUser> chattingRoomUsers, Medal medal, Interest interest, Level level) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.role = role;
        this.location = location;
        this.groupUsers = groupUsers;
        this.chattingRoomUsers = chattingRoomUsers;
        this.medal = medal;
        this.interest = interest;
        this.level = level;

    }

    public static User fromEntity(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .age(userEntity.getAge())
                .role(userEntity.getRole())
                .location(userEntity.getLocation())
                .groupUsers(
                        Optional.ofNullable(userEntity.getGroupUsers())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(GroupUser::from)
                                .collect(Collectors.toList())
                )
                .chattingRoomUsers(
                        Optional.ofNullable(userEntity.getChattingRoomUsers())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(ChattingRoomUser::from)
                                .collect(Collectors.toList())
                )
                .medal(userEntity.getMedal())
                .interest(userEntity.getInterest())
                .level(userEntity.getLevel())
                .build();
    }

    public static User registerUser(String username, String password, String nickname, int age) {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .chattingRoomUsers(new ArrayList<>())
                .age(age)
                .role(Role.ROLE_USER)
                .level(Level.BEGINER)
                .build();
    }

    public static User registerUser(String username, String password, String nickname) {
        return User.builder()
                .username(username)
                .password(password)
                .chattingRoomUsers(new ArrayList<>())
                .role(Role.ROLE_USER)
                .level(Level.BEGINER)
                .build();
    }
}
