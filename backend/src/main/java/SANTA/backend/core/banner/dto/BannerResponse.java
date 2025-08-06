package SANTA.backend.core.banner.dto;

import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;

public record BannerResponse(
        Interest interest,
        List<BannerMountainResponse> mountains
) {

    public static BannerResponse from(Interest interest, List<BannerEntity> mountains){
        return new BannerResponse(interest,mountains.stream().map(BannerMountainResponse::from).toList());
    }
}
