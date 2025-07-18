package SANTA.backend.core.stay.entity;

import SANTA.backend.core.basePlace.entity.BasePlaceEntity;
import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.stay.domain.Stay;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "STAY")
public class StayEntity extends BasePlaceEntity {

    @Builder
    protected StayEntity(Long id, Long sequence, String name, String location, String imageUrl) {
        super(id, sequence, name, location, imageUrl);
    }

    public static StayEntity from(Stay stay) {
        return StayEntity.builder()
                .id(stay.getId())
                .sequence(stay.getSequence())
                .name(stay.getName())
                .location(stay.getLocation())
                .imageUrl(stay.getImageUrl())
                .build();
    }
}
