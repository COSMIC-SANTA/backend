package SANTA.backend.core.stay.entity;

import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.stay.domain.Stay;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STAY")
public class StayEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private CourseEntity courseEntity;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected StayEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseEntity = courseEntity;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static StayEntity from(Stay stay, CourseEntity courseEntity) {
        return StayEntity.builder()
                .id(stay.getId())
                .courseEntity(courseEntity)
                .sequence(stay.getSequence())
                .name(stay.getName())
                .location(stay.getLocation())
                .imageUrl(stay.getImageUrl())
                .build();
    }
}
