package SANTA.backend.core.group.entity;

import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.core.user.domain.Level;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class GroupEntity extends BaseEntity {

    @Id @GeneratedValue
    Long id;

    String location;

    String title;

    Integer age;

    @Enumerated(EnumType.STRING)
    Interest interest;

    @Enumerated(EnumType.STRING)
    Level level;

    @OneToMany(mappedBy = "groupEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<GroupUserEntity> userGroupEntities = new ArrayList<>();

}
