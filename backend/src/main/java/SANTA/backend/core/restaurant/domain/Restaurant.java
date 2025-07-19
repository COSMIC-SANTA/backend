package SANTA.backend.core.restaurant.domain;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Restaurant extends BasePlace {

    @Builder
    protected Restaurant(Long id, Long courseId, Long sequence, String name, String location, String imageUrl, Double mapX, Double mapY) {
        super(id, courseId, sequence, name, location, imageUrl, mapX, mapY);
    }

    public static Restaurant fromEntity(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .id(restaurantEntity.getId())
                .courseId(restaurantEntity.getCourseEntity().getId())
                .name(restaurantEntity.getName())
                .sequence(restaurantEntity.getSequence())
                .location(restaurantEntity.getLocation())
                .imageUrl(restaurantEntity.getImageUrl())
                .mapX(restaurantEntity.getMapX())
                .mapY(restaurantEntity.getMapY())
                .build();
    }
}
