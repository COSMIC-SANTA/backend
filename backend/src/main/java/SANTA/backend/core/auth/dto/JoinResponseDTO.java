package SANTA.backend.core.auth.dto;

import SANTA.backend.core.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter//DTO에 무조건
public class JoinResponseDTO {

    Long userId;
    String username;
    String nickname;

    public JoinResponseDTO(User user) {
        this.userId=user.getUserId();
        this.username=user.getUsername();
        this.nickname=user.getNickname();
    }
}
