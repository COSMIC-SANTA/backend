package SANTA.backend.core.spot.entity;

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
    protected SpotEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        super(id, courseEntity, sequence, name, location, imageUrl);
    }

    public static SpotEntity from(Spot spot, CourseEntity courseEntity) {
        return SpotEntity.builder()
                .id(spot.getId())
                .courseEntity(courseEntity)
                .sequence(spot.getSequence())
                .name(spot.getName())
                .location(spot.getLocation())
                .imageUrl(spot.getImageUrl())
                .build();
    }
}
