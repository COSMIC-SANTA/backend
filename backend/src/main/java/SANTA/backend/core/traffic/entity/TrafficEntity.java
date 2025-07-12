package SANTA.backend.core.traffic.entity;

import SANTA.backend.core.traffic.domain.Traffic;
import SANTA.backend.core.traffic.domain.TrafficType;
import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TRAFFIC")
public class TrafficEntity {

    @Id @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrafficType type;

    private Long estimatedTime;

    @OneToOne(mappedBy = "trafficEntity")
    private CourseEntity courseEntity;

    @Builder
    public TrafficEntity(Long id, TrafficType type, Long estimatedTime) {
        this.id = id;
        this.type = type;
        this.estimatedTime = estimatedTime;
    }

    public static TrafficEntity from(Traffic traffic) {
        return TrafficEntity.builder()
                .id(traffic.getId())
                .type(traffic.getType())
                .estimatedTime(traffic.getEstimatedTime())
                .build();
    }
}
