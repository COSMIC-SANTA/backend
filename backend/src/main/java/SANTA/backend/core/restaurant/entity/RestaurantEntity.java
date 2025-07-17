package SANTA.backend.core.restaurant.entity;

import SANTA.backend.core.basePlace.entity.BasePlaceEntity;
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
public class RestaurantEntity extends BasePlaceEntity {

    @Builder
    protected RestaurantEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        super(id, courseEntity, sequence, name, location, imageUrl);
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
