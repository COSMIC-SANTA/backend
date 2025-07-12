package SANTA.backend.core.cafe.entity;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CAFE")
public class CafeEntity {

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
    protected CafeEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseEntity = courseEntity;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static CafeEntity from(Cafe cafe, CourseEntity courseEntity) {
        return CafeEntity.builder()
                .id(cafe.getId())
                .courseEntity(courseEntity)
                .sequence(cafe.getSequence())
                .name(cafe.getName())
                .location(cafe.getLocation())
                .imageUrl(cafe.getImageUrl())
                .build();
    }
}
