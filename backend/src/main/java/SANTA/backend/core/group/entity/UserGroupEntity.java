package SANTA.backend.core.group.entity;

import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class UserGroup extends BaseEntity{

    @Id @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    GroupEntity groupEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    UserEntity userEntity;
}
