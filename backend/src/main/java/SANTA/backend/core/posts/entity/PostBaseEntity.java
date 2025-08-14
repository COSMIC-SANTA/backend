package SANTA.backend.core.posts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class PostBaseEntity {
    @CreationTimestamp
    @Column(updatable = false) //수정 시 관여 x
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(insertable = false) //입력 시 관여 X
    private LocalDateTime updatedTime;
}
