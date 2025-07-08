package SANTA.backend.core.user.domain;

import SANTA.backend.core.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private Long userId;

    private String username;
    private String password;
    private String nickname;
    private int age;
    private Role role;
    private String location;
    private Medal medal;
    private Interest interest;
    private Level level;


    @Builder
    protected User(Long userId, String username, String password, String nickname, int age, Role role, String location, Medal medal, Interest interest, Level level) {
        this.userId=userId;
        this.username=username;
        this.password=password;
        this.nickname=nickname;
        this.age=age;
        this.role=role;
        this.location=location;
        this.medal=medal;
        this.interest=interest;
        this.level=level;

    }

    public static User fromEntity(UserEntity userEntity){
        return  User.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .age(userEntity.getAge())
                .role(userEntity.getRole())
                .location(userEntity.getLocation())
                .medal(userEntity.getMedal())
                .interest(userEntity.getInterest())
                .level(userEntity.getLevel())
                .build();
    }

    public static User registerUser( String username, String password, String nickname, int age){
        return User.builder()
                .username(username)
                .password(password)
                .age(age)
                .role(Role.ROLE_USER)
                .level(Level.BEGINER)
                .build();
    }

    public static User registerUser( String username, String password, String nickname){
        return User.builder()
                .username(username)
                .password(password)
                .role(Role.ROLE_USER)
                .level(Level.BEGINER)
                .build();
    }
}
