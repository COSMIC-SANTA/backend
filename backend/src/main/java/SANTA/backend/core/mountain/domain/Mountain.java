package SANTA.backend.core.mountain.domain;

import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.user.domain.Interest;
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

    private Interest interest;

    @Builder
    protected Mountain(Long id, Long visitCount, Long sequence, String name, Difficulty difficulty, String location, String imageUrl, Interest interest) {
        this.id = id;
        this.visitCount = visitCount;
        this.sequence = sequence;
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.imageUrl = imageUrl;
        this.interest = interest;
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
                .interest(mountainEntity.getInterest())
                .build();
    }
}
