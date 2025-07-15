package SANTA.backend.core.spot.domain;

import SANTA.backend.core.spot.entity.SpotEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Spot {

    private Long id;

    private Long courseId;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected Spot(Long id, Long courseId, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static Spot fromEntity(SpotEntity spotEntity) {
        return Spot.builder()
                .id(spotEntity.getId())
                .courseId(spotEntity.getCourseEntity().getId())
                .sequence(spotEntity.getSequence())
                .name(spotEntity.getName())
                .location(spotEntity.getLocation())
                .imageUrl(spotEntity.getImageUrl())
                .build();
    }
}
