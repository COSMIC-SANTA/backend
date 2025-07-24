package SANTA.backend.core.spot.entity;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.basePlace.entity.BasePlaceEntity;
import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.spot.domain.Spot;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SPOT")
public class SpotEntity extends BasePlaceEntity {

    @Builder
    protected SpotEntity(Long id, Long sequence, String name, String location, String imageUrl, Position position) {
        super(id, sequence, name, location, imageUrl, position);
    }

    public static SpotEntity from(Spot spot) {
        return SpotEntity.builder()
                .id(spot.getId())
                .sequence(spot.getSequence())
                .name(spot.getName())
                .location(spot.getLocation())
                .imageUrl(spot.getImageUrl())
                .position(spot.getPosition())
                .build();
    }
}
