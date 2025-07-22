package SANTA.backend.core.group.domain;

import SANTA.backend.core.group.entity.GroupUserEntity;
import SANTA.backend.core.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupUser {

    Long id;
    Group group;
    User user;

    @Builder
    private GroupUser(Long id, Group group, User user){
        this.id = id;
        this.group = group;
        this.user = user;
    }

    public static GroupUser from(GroupUserEntity groupUserEntity){
        return GroupUser.builder()
                .id(groupUserEntity.getId())
                .group(Group.fromEntity(groupUserEntity.getGroupEntity()))
                .user(User.fromEntity(groupUserEntity.getUserEntity()))
                .build();
    }
}
