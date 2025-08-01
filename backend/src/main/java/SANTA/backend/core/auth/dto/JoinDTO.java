package SANTA.backend.core.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {

    private String username;
    private String password;
    private String nickname;
    private int age;
    // username, nickname, password, age UserEntity확인
}
