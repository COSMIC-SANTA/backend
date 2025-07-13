package SANTA.backend.core.course.entity;

import SANTA.backend.core.cafe.entity.CafeEntity;
import SANTA.backend.core.course.domain.Course;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import SANTA.backend.core.spot.entity.SpotEntity;
import SANTA.backend.core.stay.entity.StayEntity;
import SANTA.backend.core.traffic.entity.TrafficEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "COURSE")
public class CourseEntity {

    @Id @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRAFFIC_ID")
    private TrafficEntity trafficEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MOUNTAIN_ID")
    private MountainEntity mountainEntity;

    @OneToMany(mappedBy = "courseEntity")
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity")
    private List<StayEntity> stays = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity")
    private List<CafeEntity> cafeEntity = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity")
    private List<SpotEntity> spotEntity = new ArrayList<>();

    private Long distance;

    @Builder
    protected CourseEntity(Long id, TrafficEntity trafficEntity, MountainEntity mountainEntity, Long distance) {
        this.id = id;
        this.trafficEntity = trafficEntity;
        this.mountainEntity = mountainEntity;
        this.distance = distance;
    }

    public static CourseEntity from(Course course, TrafficEntity trafficEntity, MountainEntity mountainEntity) {
        return CourseEntity.builder()
                .id(course.getId())
                .trafficEntity(trafficEntity)
                .mountainEntity(mountainEntity)
                .distance(course.getDistance())
                .build();
    }
}
