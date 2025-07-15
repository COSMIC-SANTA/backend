package SANTA.backend.core.mountain.entity;

import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.user.domain.Interest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MOUNTAIN")
public class MountainEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long visitCount;

    private Long sequence;

    private String name;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Builder
    protected MountainEntity(Long id, Long visitCount, Long sequence, String name, Difficulty difficulty, String location, String imageUrl, Interest interest) {
        this.id = id;
        this.visitCount = visitCount;
        this.sequence = sequence;
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.imageUrl = imageUrl;
        this.interest = interest;
    }

    public static MountainEntity from(Mountain mountain) {
        return MountainEntity.builder()
                .id(mountain.getId())
                .visitCount(mountain.getVisitCount())
                .sequence(mountain.getSequence())
                .name(mountain.getName())
                .difficulty(mountain.getDifficulty())
                .location(mountain.getLocation())
                .imageUrl(mountain.getImageUrl())
                .interest(mountain.getInterest())
                .build();
    }
}
