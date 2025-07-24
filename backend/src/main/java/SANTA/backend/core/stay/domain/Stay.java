package SANTA.backend.core.stay.domain;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.stay.entity.StayEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Stay extends BasePlace {

    @Builder
    protected Stay(Long id, Long courseId, Long sequence, String name, String location, String imageUrl, Position position) {
        super(id, courseId, sequence, name, location, imageUrl, position);
    }

    public static Stay fromEntity(StayEntity stayEntity){
        return Stay.builder()
                .id(stayEntity.getId())
                .courseId(stayEntity.getCourseEntity().getId())
                .sequence(stayEntity.getSequence())
                .name(stayEntity.getName())
                .location(stayEntity.getLocation())
                .imageUrl(stayEntity.getImageUrl())
                .position(stayEntity.getPosition())
                .build();
    }
}
