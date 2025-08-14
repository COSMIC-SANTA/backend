package SANTA.backend.core.restaurant.domain;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Restaurant extends BasePlace {

    @Builder
    protected Restaurant(Long id, Long courseId, Long sequence, String name, String location, String imageUrl, Position position) {
        super(id, courseId, sequence, name, location, imageUrl, position);
    }

    public static Restaurant fromEntity(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .id(restaurantEntity.getId())
                .courseId(restaurantEntity.getCourseEntity().getId())
                .name(restaurantEntity.getName())
                .sequence(restaurantEntity.getSequence())
                .location(restaurantEntity.getLocation())
                .imageUrl(restaurantEntity.getImageUrl())
                .position(restaurantEntity.getPosition())
                .build();
    }
}
