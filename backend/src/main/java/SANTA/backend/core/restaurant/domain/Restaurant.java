package SANTA.backend.core.restaurant.domain;

import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Restaurant {

    private Long id;

    private Long courseId;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected Restaurant(Long id, Long courseId, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static Restaurant fromEntity(RestaurantEntity restaurantEntity) {
        return Restaurant.builder()
                .id(restaurantEntity.getId())
                .courseId(restaurantEntity.getCourseEntity().getId())
                .name(restaurantEntity.getName())
                .sequence(restaurantEntity.getSequence())
                .location(restaurantEntity.getLocation())
                .imageUrl(restaurantEntity.getImageUrl())
                .build();
    }
}
