package SANTA.backend.core.plan.domain;

import SANTA.backend.core.plan.entity.PlanEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Plan {

    private Long id;

    private Long userId;

    private Long courseId;

    private PlanState state;

    private LocalDateTime targetDate;

    @Builder
    protected Plan(Long id, Long userId, Long courseId, PlanState state, LocalDateTime targetDate) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.state = state;
        this.targetDate = targetDate;
    }

    public static Plan fromEntity(PlanEntity planEntity) {
        return Plan.builder()
                .id(planEntity.getId())
                .userId(planEntity.getUser().getId())
                .courseId(planEntity.getCourse().getId())
                .state(planEntity.getState())
                .targetDate(planEntity.getTargetDate())
                .build();
    }
}
