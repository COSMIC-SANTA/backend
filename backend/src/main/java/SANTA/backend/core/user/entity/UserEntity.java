package SANTA.backend.core.user.entity;

import SANTA.backend.core.group.entity.UserGroupEntity;
import SANTA.backend.core.user.domain.Role;
import SANTA.backend.core.user.domain.User;
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
    private Role role;

    @OneToMany(mappedBy = "USER_ID")
    private List<UserGroupEntity> userGroups = new ArrayList<>();

    @Builder
    public UserEntity(Long userId, String username, String password, String nickname, int age, Role role){
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.role = role;
    }

    public static UserEntity from(User user){
        return UserEntity.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .age(user.getAge())
                .role(user.getRole())
                .build();
    }

}
