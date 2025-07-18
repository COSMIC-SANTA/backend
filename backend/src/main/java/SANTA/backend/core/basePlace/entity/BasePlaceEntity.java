package SANTA.backend.core.basePlace.entity;

import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor
public abstract class BasePlaceEntity {
    @Id
    @GeneratedValue
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    protected CourseEntity courseEntity;

    protected Long sequence;

    protected String name;

    protected String location;

    protected String imageUrl;

    protected BasePlaceEntity(Long id, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }


    public void assignToCourse(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }
}
