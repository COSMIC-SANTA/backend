package SANTA.backend.core.group.domain;

import SANTA.backend.core.group.entity.GroupEntity;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.core.user.domain.Level;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Group {

    Long id;

    String location;

    String title;

    Integer age;

    Interest interest;

    Level level;

    @Builder
    protected Group(Long id, String location, String title, Integer age, Interest interest, Level level) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.age = age;
        this.interest = interest;
        this.level = level;
    }

    public static Group fromEntity(GroupEntity groupEntity) {
        return Group.builder()
                .id(groupEntity.getId())
                .location(groupEntity.getLocation())
                .title(groupEntity.getTitle())
                .age(groupEntity.getAge())
                .interest(groupEntity.getInterest())
                .level(groupEntity.getLevel())
                .build();
    }
}
