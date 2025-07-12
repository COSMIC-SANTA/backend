package SANTA.backend.core.stay.domain;

import SANTA.backend.core.stay.entity.StayEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Stay {

    private Long id;

    private Long courseId;

    private Long sequence;

    private String name;

    private String location;

    private String imageUrl;

    @Builder
    protected Stay(Long id, Long courseId, Long sequence, String name, String location, String imageUrl) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public static Stay fromEntity(StayEntity stayEntity){
        return Stay.builder()
                .id(stayEntity.getId())
                .courseId(stayEntity.getCourseEntity().getId())
                .sequence(stayEntity.getSequence())
                .name(stayEntity.getName())
                .location(stayEntity.getLocation())
                .imageUrl(stayEntity.getImageUrl())
                .build();
    }
}
