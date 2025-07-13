package SANTA.backend.core.restaurant.entity;

import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "RESTAURANT")
public class RestaurantEntity {

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
    protected RestaurantEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseEntity = courseEntity;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static RestaurantEntity from (Restaurant restaurant, CourseEntity courseEntity) {
        return RestaurantEntity.builder()
                .id(restaurant.getId())
                .courseEntity(courseEntity)
                .sequence(restaurant.getSequence())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .imageUrl(restaurant.getImageUrl())
                .build();
    }
}
