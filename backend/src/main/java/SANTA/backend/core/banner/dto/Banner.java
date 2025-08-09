package SANTA.backend.core.banner.dto;

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

    @Builder
    public Banner(Long code, String name, String location, Interest interest, String imageUrl, Difficulty difficulty){
        this.code = code;
        this.name= name;
        this.location = location;
        this.interest = interest;
        this.imageUrl = imageUrl;
        this.difficulty = difficulty;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
