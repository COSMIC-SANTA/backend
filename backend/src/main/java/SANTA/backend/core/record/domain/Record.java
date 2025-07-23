package SANTA.backend.core.record.domain;

import SANTA.backend.core.record.entity.RecordEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Record {

    private Long id;

    private Long userId;

    private Long distance;

    private Long duration;

    private Long step;

    @Builder
    protected Record(Long id, Long userId, Long distance, Long duration, Long step) {
        this.id = id;
        this.userId = userId;
        this.distance = distance;
        this.duration = duration;
        this.step = step;
    }

    public static Record fromEntity(RecordEntity recordEntity) {
        return Record.builder()
                .id(recordEntity.getRecordId())
                .userId(recordEntity.getUser().getId())
                .distance(recordEntity.getDistance())
                .step(recordEntity.getStep())
                .build();
    }
}
