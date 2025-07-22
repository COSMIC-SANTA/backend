package SANTA.backend.core.group.entity;

import SANTA.backend.core.group.domain.GroupUser;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "group_user_entity")
@NoArgsConstructor
public class GroupUserEntity extends BaseEntity{

    @Id @GeneratedValue @Column(name = "group_user_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    GroupEntity groupEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    UserEntity userEntity;

    @Builder
    private GroupUserEntity(Long id, GroupEntity groupEntity, UserEntity userEntity){
        this.id = id;
        this.groupEntity = groupEntity;
        this.userEntity = userEntity;
    }

    public static GroupUserEntity from(GroupUser groupUser){
        return GroupUserEntity.builder()
                .groupEntity(GroupEntity.from(groupUser.getGroup()))
                .build();
    }
}
