package SANTA.backend.core.record.domain;

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
}
