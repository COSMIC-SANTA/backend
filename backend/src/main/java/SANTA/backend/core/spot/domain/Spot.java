package SANTA.backend.core.spot.domain;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.spot.entity.SpotEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Spot extends BasePlace {

    @Builder
    protected Spot(Long id, Long courseId, Long sequence, String name, String location, String imageUrl, Position position) {
        super(id, courseId, sequence, name, location, imageUrl, position);
    }

    public static Spot fromEntity(SpotEntity spotEntity) {
        return Spot.builder()
                .id(spotEntity.getId())
                .courseId(spotEntity.getCourseEntity().getId())
                .sequence(spotEntity.getSequence())
                .name(spotEntity.getName())
                .location(spotEntity.getLocation())
                .imageUrl(spotEntity.getImageUrl())
                .position(spotEntity.getPosition())
                .build();
    }
}
