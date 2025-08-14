package SANTA.backend.core.restaurant.entity;

import SANTA.backend.core.basePlace.domain.Position;
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
    protected RestaurantEntity(Long id, Long sequence, String name, String location, String imageUrl, Position position) {
        super(id, sequence, name, location, imageUrl, position);
    }

    public static RestaurantEntity from (Restaurant restaurant) {
        return RestaurantEntity.builder()
                .id(restaurant.getId())
                .sequence(restaurant.getSequence())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .imageUrl(restaurant.getImageUrl())
                .position(restaurant.getPosition())
                .build();
    }
}
