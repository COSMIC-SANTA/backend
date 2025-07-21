package SANTA.backend.core.chatting.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "chatting")
public class ChattingEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToMany
    private List<UserChattingEntity> userChattingEntities = new ArrayList<>();

    private String title;

    private String subTitle;

    private String clientUrl;

}
