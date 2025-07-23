package SANTA.backend.core.group.entity;

import SANTA.backend.core.group.domain.Group;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.core.user.domain.Level;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "group_entity")
@NoArgsConstructor
public class GroupEntity extends BaseEntity {

    @Id
    @GeneratedValue
    Long id;

    String location;

    String title;

    Integer age;

    @Enumerated(EnumType.STRING)
    Interest interest;

    @Enumerated(EnumType.STRING)
    Level level;

    @OneToMany(mappedBy = "groupEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    List<GroupUserEntity> groupUserEntities = new ArrayList<>();

    @Builder
    private GroupEntity(Long id, String location, String title, Integer age, Interest interest, Level level, List<GroupUserEntity> groupUserEntities) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.age = age;
        this.interest = interest;
        this.level = level;
        this.groupUserEntities = groupUserEntities;
    }

    public static GroupEntity from(Group group) {
        return GroupEntity.builder()
                .location(group.getLocation())
                .title(group.getTitle())
                .age(group.getAge())
                .interest(group.getInterest())
                .level(group.getLevel())
                .build();
    }
}
