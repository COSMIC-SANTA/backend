package SANTA.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {

    @Id
    private String userId; //->이거 string형임

    private String username;
    private String password;
    private String nickname;
    private int age;
    private String role;
}
