package SANTA.backend.core.domain;

import SANTA.backend.core.entity.UserEntity;
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

    @Builder
    protected User(Long userId, String username, String password, String nickname, int age, Role role){
        this.userId=userId;
        this.username=username;
        this.password=password;
        this.nickname=nickname;
        this.age=age;
        this.role=role;
    }

    public static User fromEntity(UserEntity userEntity){
        return  User.builder()
                .userId(userEntity.getUserId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .nickname(userEntity.getNickname())
                .age(userEntity.getAge())
                .role(userEntity.getRole())
                .build();

    }

    public static User registerUser( String username, String password, String nickname, int age){
        return User.builder()
                .username(username)
                .password(password)
                .age(age)
                .role(Role.ROLE_USER)
                .build();
    }

    public static User registerUser( String username, String password, String nickname){
        return User.builder()
                .username(username)
                .password(password)
                .role(Role.ROLE_USER)
                .build();
    }
}
