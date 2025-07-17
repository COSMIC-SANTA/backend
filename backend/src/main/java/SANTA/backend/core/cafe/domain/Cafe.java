package SANTA.backend.core.cafe.domain;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.cafe.entity.CafeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Cafe extends BasePlace {

    @Builder
    protected Cafe(Long id, Long courseId, Long sequence, String name, String location, String imageUrl) {
        super(id, courseId, sequence, name, location, imageUrl);
    }

    public static Cafe fromEntity(CafeEntity cafeEntity) {
        return Cafe.builder()
                .id(cafeEntity.getId())
                .courseId(cafeEntity.getCourseEntity().getId())
                .sequence(cafeEntity.getSequence())
                .name(cafeEntity.getName())
                .location(cafeEntity.getLocation())
                .imageUrl(cafeEntity.getImageUrl())
                .build();
    }
}
