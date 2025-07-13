package SANTA.backend.core.spot.entity;

import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.spot.domain.Spot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SPOT")
public class SpotEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity courseEntity;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected SpotEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseEntity = courseEntity;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static SpotEntity from(Spot spot, CourseEntity courseEntity) {
        return SpotEntity.builder()
                .id(spot.getId())
                .courseEntity(courseEntity)
                .sequence(spot.getSequence())
                .name(spot.getName())
                .location(spot.getLocation())
                .imageUrl(spot.getImageUrl())
                .build();
    }
}
