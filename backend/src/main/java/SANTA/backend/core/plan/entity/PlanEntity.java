package SANTA.backend.core.plan.entity;

import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.plan.domain.Plan;
import SANTA.backend.core.plan.domain.PlanState;
import SANTA.backend.core.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PLAN")
public class PlanEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity course;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE")
    private PlanState state;

    private LocalDateTime targetDate;

    @Builder
    public PlanEntity(Long id, UserEntity user, CourseEntity course, PlanState state, LocalDateTime targetDate) {
        this.id = id;
        this.user = user;
        this.course = course;
        this.state = state;
        this.targetDate = targetDate;
    }

    public static PlanEntity from(Plan plan, UserEntity user, CourseEntity course) {
        return PlanEntity.builder()
                .id(plan.getId())
                .user(user)
                .course(course)
                .state(plan.getState())
                .targetDate(plan.getTargetDate())
                .build();
    }

    public void completePlan() {
        this.state = PlanState.COMPLETED;
    }
}
