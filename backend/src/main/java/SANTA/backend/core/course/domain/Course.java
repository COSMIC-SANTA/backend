package SANTA.backend.core.course.domain;

import SANTA.backend.core.course.entity.CourseEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Course {
    private Long id;

    private Long trafficId;

    private Long mountainId;

    private Long distance;

    @Builder
    protected Course(Long id, Long trafficId, Long mountainId, Long distance) {
        this.id = id;
        this.trafficId = trafficId;
        this.mountainId = mountainId;
        this.distance = distance;
    }

    public static Course fromEntity(CourseEntity courseEntity) {
        return Course.builder()
                .id(courseEntity.getId())
                .mountainId(courseEntity.getMountainEntity().getId())
                .distance(courseEntity.getDistance())
                .build();
    }
}
