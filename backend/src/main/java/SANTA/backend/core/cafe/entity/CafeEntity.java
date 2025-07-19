package SANTA.backend.core.cafe.entity;

import SANTA.backend.core.basePlace.entity.BasePlaceEntity;
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
public class CafeEntity extends BasePlaceEntity {

    @Builder
    protected CafeEntity(Long id, Long sequence, String name, String location, String imageUrl, Double mapX, Double mapY) {
        super(id, sequence, name, location, imageUrl, mapX, mapY);
    }

    public static CafeEntity from(Cafe cafe) {
        return CafeEntity.builder()
                .id(cafe.getId())
                .sequence(cafe.getSequence())
                .name(cafe.getName())
                .location(cafe.getLocation())
                .imageUrl(cafe.getImageUrl())
                .mapX(cafe.getMapX())
                .mapY(cafe.getMapY())
                .build();
    }
}
