package SANTA.backend.core.entity;

import SANTA.backend.core.domain.Role;
import SANTA.backend.core.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter//Entity는 setter설정XXX
public class UserEntity {

    @Id @GeneratedValue
    private Long userId; //->이거 string형임

    private String username;
    private String password;
    private String nickname;
    private int age;
    private Role role;

    protected UserEntity(){

    }

    public UserEntity(User user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.role = user.getRole();
    }

}
