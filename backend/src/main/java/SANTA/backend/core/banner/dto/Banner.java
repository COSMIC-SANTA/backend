package SANTA.backend.core.banner.dto;

import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.user.domain.Interest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Banner {

    private Long code;
    private String name;
    private String location;
    private Interest interest;
    private String imageUrl;
    private Difficulty difficulty;
    private Long viewCount;

    @Builder
    public Banner(Long code, String name, String location, Interest interest, String imageUrl, Difficulty difficulty, Long  viewCount) {
        this.code = code;
        this.name= name;
        this.location = location;
        this.interest = interest;
        this.imageUrl = imageUrl;
        this.difficulty = difficulty;
        this.viewCount = viewCount;
    }

    public static Banner fromEntity(BannerEntity bannerEntity) {
        return Banner.builder()
                .name(bannerEntity.getName())
                .location(bannerEntity.getLocation())
                .interest(bannerEntity.getInterest())
                .imageUrl(bannerEntity.getImageUrl())
                .difficulty(bannerEntity.getDifficulty())
                .viewCount(bannerEntity.getViewCount())
                .build();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setViewCount(Long viewCount) {this.viewCount = viewCount;}
}
