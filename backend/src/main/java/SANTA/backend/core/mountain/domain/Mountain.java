package SANTA.backend.core.mountain.domain;

import SANTA.backend.core.mountain.entity.MountainEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Mountain {

    private Long id;

    private Long visitCount;

    private Long sequence;

    private String name;

    private Difficulty difficulty;

    private String location;

    private String imageUrl;

    private Long interestId;

    @Builder
    protected Mountain(Long id, Long visitCount, Long sequence, String name, Difficulty difficulty, String location, String imageUrl, Long interestId) {
        this.id = id;
        this.visitCount = visitCount;
        this.sequence = sequence;
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.imageUrl = imageUrl;
        this.interestId = interestId;
    }

    public static Mountain fromEntity(MountainEntity mountainEntity) {
        return Mountain.builder()
                .id(mountainEntity.getId())
                .visitCount(mountainEntity.getVisitCount())
                .sequence(mountainEntity.getSequence())
                .name(mountainEntity.getName())
                .difficulty(mountainEntity.getDifficulty())
                .location(mountainEntity.getLocation())
                .imageUrl(mountainEntity.getImageUrl())
                .build();
    }
}
