package SANTA.backend.core.record.entity;

import SANTA.backend.core.ranking.entity.RankingEntity;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.core.record.domain.Record;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "RECORD")
@NoArgsConstructor
public class RecordEntity {

    @Id @GeneratedValue
    private Long recordId;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    private Long distance;

    private Long duration;

    private Long step;

    @OneToMany(mappedBy = "record")
    private List<RankingEntity> rankings = new ArrayList<>();

    @Builder
    public RecordEntity(Long recordId, UserEntity user, Long distance, Long duration, Long step) {
        this.recordId = recordId;
        this.user = user;
        this.distance = distance;
        this.duration = duration;
        this.step = step;
    }

    public static RecordEntity from(Record record, UserEntity user) {
        return RecordEntity.builder()
                .recordId(record.getId())
                .user(user)
                .distance(record.getDistance())
                .duration(record.getDuration())
                .step(record.getStep())
                .build();
    }
}
