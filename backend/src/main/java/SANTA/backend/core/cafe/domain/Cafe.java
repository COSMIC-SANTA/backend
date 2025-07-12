package SANTA.backend.core.cafe.domain;

import SANTA.backend.core.cafe.entity.CafeEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Cafe {

    private Long id;

    private Long courseId;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected Cafe(Long id, Long courseId, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
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
