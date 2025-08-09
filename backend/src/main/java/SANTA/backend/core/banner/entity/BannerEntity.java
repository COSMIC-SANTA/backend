package SANTA.backend.core.banner.entity;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.user.domain.Interest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Banner")
@Getter
@NoArgsConstructor
public class BannerEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Interest interest;

    @Builder
    private BannerEntity(String name, Difficulty difficulty, String location, String imageUrl, Interest interest){
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.imageUrl = imageUrl;
        this.interest = interest;
    }

    public static BannerEntity from(Banner banner){
        return BannerEntity.builder()
                .name(banner.getName())
                .location(banner.getLocation())
                .interest(banner.getInterest())
                .difficulty(banner.getDifficulty())
                .build();
    }
}
