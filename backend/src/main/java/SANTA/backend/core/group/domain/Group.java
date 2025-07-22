package SANTA.backend.core.group.domain;

import SANTA.backend.core.group.entity.GroupEntity;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.core.user.domain.Level;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
public class Group {

    Long id;

    String location;

    String title;

    Integer age;

    Interest interest;

    Level level;

    List<GroupUser> groupUsers;


    @Builder
    protected Group(Long id, String location, String title, Integer age, Interest interest, Level level, List<GroupUser> groupUsers) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.age = age;
        this.interest = interest;
        this.level = level;
        this.groupUsers = groupUsers;
    }

    public static Group fromEntity(GroupEntity groupEntity) {
        return Group.builder()
                .id(groupEntity.getId())
                .location(groupEntity.getLocation())
                .title(groupEntity.getTitle())
                .age(groupEntity.getAge())
                .interest(groupEntity.getInterest())
                .level(groupEntity.getLevel())
                .groupUsers(
                        Optional.ofNullable(groupEntity.getGroupUserEntities())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(GroupUser::from)
                                .toList()
                )
                .build();
    }
}
